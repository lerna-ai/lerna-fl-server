
## Create the bash script test.sh:
>```
> #!/bin/bash
> curl http://172.17.0.1:8080/api/v1/admin/training/runFL/all?token=admin-token
>```

## Add it to the scheduler:


> crontab -e

> add line: 0 12,18,0,6 * * * ~/Downloads/test.sh >> ~/Downloads/cron.txt 2>&1


## Done!
