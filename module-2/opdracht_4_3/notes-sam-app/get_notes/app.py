import json
import boto3
from aws_xray_sdk.core import patch_all
from boto3.dynamodb.conditions import Key, Attr

patch_all()
dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('notes')


def lambda_handler(event, context):
    print(f"{event = }")
    user_id = event['pathParameters']['user_id']

    response = table.query(
        KeyConditionExpression=Key('PK').eq(f"USER#{user_id}") & Key('SK').begins_with("NOTE#"),
    )
    print(f"{response = }")

    return {
        "statusCode": 200,
        "body": json.dumps(response['Items']),
        "headers": {
            "content-type": "application/json"
        },
        "isBase64Encoded": False
    }
