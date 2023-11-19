import json
from os import environ
import boto3
from botocore.exceptions import ClientError
from aws_xray_sdk.core import patch_all

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table(environ['CONNECTIONS_TABLE_NAME'])


def lambda_handler(event, context):
    print(f"{event = }")
    eventData = json.dumps(json.loads(event['body'])['data'])
    print(f"Websocket post data = {eventData}")
    allConnections = []

    try:
        response = table.scan()
        print(f"{response = }")
        allConnections = response["Items"]
    except ClientError as error:
        print(f"{error = }")
        return {
            'statusCode': 500,
            'body': 'Failed to receive connections!'
        }

    domain_name = event['requestContext']['domainName']
    stage = event['requestContext']['stage']
    gateway_management_api = boto3.client('apigatewaymanagementapi', endpoint_url=f"https://{domain_name}/{stage}")

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

    return {
        'statusCode': 200,
        'body': 'Message send.'
    }
