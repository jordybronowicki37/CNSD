import json
import boto3
import uuid
from aws_xray_sdk.core import patch_all

patch_all()
dynamodb = boto3.resource('dynamodb')


def lambda_handler(event, context):
    print(f"{event =} {type(event)}")

    user_id = event['pathParameters']['user_id']
    body = json.loads(event["body"])

    table = dynamodb.Table('notes')

    item = {
        'PK': f'USER#{user_id}',
        'SK': f'NOTE#{str(uuid.uuid4())}',
        'text': body["text"],
        'Type': 'NOTE'
    }

    response = table.put_item(Item=item)
    print(f"{response =} {type(response)}")

    return {
        "statusCode": 200,
        "body": json.dumps(item)
    }
