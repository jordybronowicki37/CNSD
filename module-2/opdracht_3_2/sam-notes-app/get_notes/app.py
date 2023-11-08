import json
import boto3
from aws_xray_sdk.core import patch_all
from boto3.dynamodb.conditions import Key, Attr

patch_all()
dynamodb = boto3.resource('dynamodb')


def lambda_handler(event, context):
    print(f"{event =} {type(event)}")
    user_id = event['pathParameters']['user_id']

    table = dynamodb.Table('notes')
    notes = []
    done = False

    while not done:
        response = table.scan(FilterExpression=Key('PK').begins_with(f"USER#{user_id}") & Attr('Type').eq('NOTE'))
        print(f"{response}")
        notes += response['Items']
        start_key = response.get('LastEvaluatedKey', None)
        done = start_key is None

    return {
        "statusCode": 200,
        "body": json.dumps(notes)
    }
