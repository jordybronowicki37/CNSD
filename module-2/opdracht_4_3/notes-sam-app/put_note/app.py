from os import environ
import json
import boto3
from botocore.exceptions import ClientError
from aws_xray_sdk.core import patch_all
from notifyUsers import notify_users

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table(environ['NOTES_TABLE_NAME'])


def lambda_handler(event, context):
    print(f"{event = }")

    body = json.loads(event["body"])
    print(f"{body = }")

    # Triggered over http
    if "pathParameters" in event:
        user_id = event['pathParameters']['user_id']
        note_id = event['pathParameters']['note_id']
    # Triggered over WS
    else:
        user_id = body['user_id']
        note_id = body['note_id']

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

        notify_users(event, "note/updated", response['Attributes'])

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
