(1) Deploy Lambda with AWS CLI

> aws lambda update-function-code --function-name EmployeeCRUD --zip-file fileb://target/LamdaExOne-1.0-SNAPSHOT.jar --region ap-southeast-1

(2) AWS Lambda log tail

> aws logs tail "/aws/lambda/EmployeeCRUD" --follow  --region ap-southeast-1
> 
> 
# Git
In case, if you encounter git permission issues since you have configured two github locally, simply reset the origin.
> git remote set-url origin git@github.com-personal:semika/lambda-aws-ex-one.git