from os import environ
import json
import boto3
import uuid
from aws_xray_sdk.core import patch_all

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table(environ['NOTES_TABLE_NAME'])


def lambda_handler(event, context):
    print(f"{event = }")
    user_id = event['pathParameters']['user_id']
    body = json.loads(event["body"])

    item = {
        'PK': f'USER#{user_id}',
        'SK': f'NOTE#{str(uuid.uuid4())}',
        'Text': body["text"],
        'Type': 'NOTE'
    }

    response = table.put_item(Item=item)
    print(f"{response = }")

    return {
        "statusCode": 201,
        "body": json.dumps(item),
        "headers": {
            "content-type": "application/json"
        },
        "isBase64Encoded": False
    }
