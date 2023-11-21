from __future__ import print_function
from aws_xray_sdk.core import patch_all

import boto3
import json
import logging
import random

# Enable XRay tracing on client libraries
logger = logging.getLogger()
logger.setLevel(logging.INFO)
patch_all()

# Initialize the clients
step_functions = boto3.client('stepfunctions')


def lambda_handler(event, context):
    logger.info(f"{event = } {type(event)}")

    for record in event["Records"]:

        # Get event body
        body = json.loads(record["body"])
        task_token = body["TaskToken"]
        logger.info(f"@TaskToken: {task_token}")

        message = body["Message"]
        logger.info(f"@Message: {message}")

        response_output = {
            "message": message
        }

        if random.randint(0, 3) == 1:
            step_functions.send_task_failure(
                taskToken=task_token,
                error="Some unknown error occured!"
            )
        else:
            step_functions.send_task_success(
                taskToken=task_token,
                output=json.dumps(response_output)
            )
