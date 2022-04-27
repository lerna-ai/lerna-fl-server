## Create trigger in postgres:



>```
> CREATE EXTENSION adminpack;
>```
>```
> CREATE OR REPLACE FUNCTION mytrigger()
>  RETURNS trigger
>  LANGUAGE plpgsql
> AS $function$
> BEGIN
>  PERFORM pg_catalog.pg_file_write('outputlog', CONCAT(NEW.token,E'\n')::text, true);
>  return NEW;
> END
> $function$
>```
>```
> CREATE TRIGGER myTrigger AFTER INSERT ON lerna_app
>     FOR EACH ROW EXECUTE PROCEDURE mytrigger();
>```   

## Expose the docker directory to the shell:


> docker run -v ~/Downloads:/var/lib/postgresql/data ??
> Add file permissions?


## Create the bash script test.sh:
>```
> #!/bin/bash
> filename='/home/gkellaris/Downloads/outputlog'
> while read line; do
> curl http://172.17.0.1:8080/api/v1/admin/training/runFL/"$line"?token=admin-token
> done < $filename
>```

## Add it to the scheduler:


> crontab -e

> add line: 0 12,18,0,6 * * * ~/Downloads/test.sh >> ~/Downloads/cron.txt 2>&1


## Done!
