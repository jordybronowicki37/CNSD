import json
import boto3
from aws_xray_sdk.core import patch_all

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('notes')


def lambda_handler(event, context):
    print(f"{event = }")
    user_id = event['pathParameters']['user_id']
    note_id = event['pathParameters']['note_id']

    response = table.get_item(Key={
        'PK': f'USER#{user_id}',
        'SK': f'NOTE#{note_id}'
    })

    print(f"{response = }")

    if 'Item' in response:
        return {
            "statusCode": 200,
            "body": json.dumps(response['Item']),
            "headers": {
                "content-type": "application/json"
            },
            "isBase64Encoded": False
        }

    return {
        "statusCode": 404
    }
