import json
import boto3
from os import environ

dynamodb = boto3.resource('dynamodb')
connectionsTable = dynamodb.Table(environ['CONNECTIONS_TABLE_NAME'])
gateway_management_api = boto3.client('apigatewaymanagementapi', endpoint_url=environ["WS_API_GATEWAY_URL"])


def notify_users(event, action, data):
    try:
        response = connectionsTable.scan()
        allConnections = response["Items"]
        domain_name = event['requestContext']['domainName']
        stage = event['requestContext']['stage']
        print(f"Endpoint = https://{domain_name}/{stage}")
        eventData = json.dumps({
            "action": action,
            "data": data
        })

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
                    connectionsTable.delete_item(Key={'connectionId': connectionId})
                else:
                    print(f"Error sending message to websocket: {error}")

    except Exception as error:
        print(f"An error occurred during notifying. {error = }")
