import json
from os import environ
import boto3
from aws_xray_sdk.core import patch_all
from notifyUsers import notify_users

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table(environ['NOTES_TABLE_NAME'])


def lambda_handler(event, context):
    print(f"{event = }")

    # Triggered over http
    if "pathParameters" in event:
        user_id = event['pathParameters']['user_id']
        note_id = event['pathParameters']['note_id']
    # Triggered over WS
    else:
        body = json.loads(event["body"])
        print(f"{body = }")
        user_id = body['user_id']
        note_id = body['note_id']

    item = {
        'PK': f'USER#{user_id}',
        'SK': f'NOTE#{note_id}'
    }
    response = table.delete_item(
        Key=item,
        ReturnValues='ALL_OLD'
    )
    print(f"{response = }")

    notify_users(event, "note/deleted", item)

    if 'Attributes' not in response:
        return {
            'statusCode': 404
        }

    return {
        'statusCode': 204
    }
