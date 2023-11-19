import json
import boto3
import uuid
from aws_xray_sdk.core import patch_all

patch_all()
dynamodb = boto3.resource('dynamodb')


def lambda_handler(event, context):
    print(f"{event = }")
    user_id = event['pathParameters']['user_id']
    body = json.loads(event["body"])

    table = dynamodb.Table('notes')

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
