from os import environ
import json
import boto3
from botocore.exceptions import ClientError
from aws_xray_sdk.core import patch_all

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table(environ['NOTES_TABLE_NAME'])
connectionsTable = dynamodb.Table(environ['CONNECTIONS_TABLE_NAME'])


def notify_users(event, item):
    try:
        response = table.scan()
        allConnections = response["Items"]
        domain_name = event['requestContext']['domainName']
        stage = event['requestContext']['stage']
        gateway_management_api = boto3.client('apigatewaymanagementapi', endpoint_url=f"https://{domain_name}/{stage}")
        eventData = json.dumps({
            "action": "changed_note",
            "data": item
        })

        for c in allConnections:
            connectionId = c["connectionId"]
            try:
                gateway_management_api.post_to_connection(
                    Data=eventData,
                    ConnectionId=connectionId
                )
            except Exception as error:
                if type(error).__name__ == "GoneException":
                    print(f"Found stale connection, deleting {connectionId}")
                    table.delete_item(Key={'connectionId': connectionId})
                else:
                    print(f"Error sending message to websocket: {error}")

    except Exception as error:
        print(f"An error occurred during notifying. {error = }")


def lambda_handler(event, context):
    print(f"{event = }")
    user_id = event['pathParameters']['user_id']
    note_id = event['pathParameters']['note_id']
    body = json.loads(event["body"])
    print(f"{body = }")

    try:
        response = table.update_item(
            Key={
                'PK': f'USER#{user_id}',
                'SK': f'NOTE#{note_id}'
            },
            ConditionExpression='attribute_exists(PK) and attribute_exists(SK)',
            UpdateExpression="set #txt = :t",
            ExpressionAttributeValues={
                ':t': body["text"]
            },
            ExpressionAttributeNames={
                '#txt': "Text"
            },
            ReturnValues="ALL_NEW"
        )
        print(f"{response = }")

        notify_users(event, response['Attributes'])

        return {
            'statusCode': 200,
            'body': json.dumps(response['Attributes']),
            'headers': {
                'content-type': 'application/json'
            },
            "isBase64Encoded": False
        }
    except ClientError as e:
        if e.response['Error']['Code'] == 'ConditionalCheckFailedException':
            return {
                "statusCode": 404
            }

    return {
        "statusCode": 500
    }
