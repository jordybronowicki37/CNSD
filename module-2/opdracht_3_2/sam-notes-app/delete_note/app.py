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

    response = table.delete_item(
        Key={
            'PK': f'USER#{user_id}',
            'SK': f'NOTE#{note_id}'
        },
        ReturnValues='ALL_OLD'
    )
    print(f"{response = }")

    if 'Attributes' not in response:
        return {
            'statusCode': 404
        }

    return {
        'statusCode': 204
    }
