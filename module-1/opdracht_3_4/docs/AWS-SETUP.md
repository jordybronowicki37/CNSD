
## Connect to EC2 via ssh
1. Achieve root permissions: `sudo su`
2. Create or get the keypair and save it somewhere on the local linux file system. In this example it is saved under :`/credentials/ec2-keypair.pem`
3. Change the permissions of the file: `chmod 400 /credentials/ec2-keypair.pem`
4. Check the permissions: `ls -l /credentials/ec2-keypair.pem`
5. Connect via ssh: `ssh -i /credentials/ec2-keypair.pem ec2-user@$IP` (Change '$IP' to the public ip of the instance)

## Install docker on EC2
[Guide](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/create-container-image.html#create-container-image-install-docker)
