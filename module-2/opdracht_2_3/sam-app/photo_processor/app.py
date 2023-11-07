from __future__ import print_function
import os
import boto3
import json
import base64

xrayPatched = False


def lambda_handler(event, context):
    print("Deployed automatically with Jenkins")
    s3 = boto3.resource('s3')
    sns = boto3.client('sns')

    # Read the configuration properties
    destBucketname = os.environ['DESTINATION_S3_BUCKETNAME']
    print(f"{destBucketname =}")

    snsTopicArn = os.environ['SNS_TOPIC_ARN']
    print(f"{snsTopicArn =}")

    # Process each message
    for record in event['Records']:
        # Read the image message from the record (extended message)
        print(f"{record} {type(record)}")
        body = record["body"]
        print(f"{body} {type(body)}")
        extmessage = json.loads(body)
        print(f"{extmessage} {type(extmessage)}")
        bucketname = extmessage[1]["s3BucketName"]
        itemname = extmessage[1]["s3Key"]
        print(f"Extended message body at S3 {bucketname}:{itemname}")
        srcObj = s3.Object(bucketname, itemname)
        base64_img = srcObj.get()['Body'].read()
        decoded_image_data = base64.decodebytes(base64_img)
        print(f"Retreived and decoded the message body")

        # Save the image to the Photo Processor S3 bucket
        destItemname = itemname + ".jpg"
        destObj = s3.Object(destBucketname, destItemname)
        destObj.put(Body=decoded_image_data, ACL='public-read')
        objectUrl = f"https://{destBucketname}.s3.amazonaws.com/{destItemname}";
        print(f"Image processed and stored at {objectUrl}")

        # Send notification to email subscribers
        sns.publish(TopicArn=snsTopicArn, Message=f"Photo processed and available for download.{objectUrl}",
                    Subject="Awesome photo processing done")
        print(f"Notifitation send to email subscribers")

        # Delete the S3 content of the extended message
        srcObj.delete()
        print(f"Extended message body deleted {bucketname}:{itemname}")
