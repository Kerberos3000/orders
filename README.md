# Orders 

## Link to original Repository
This is a forked repository of:
[Link to Repo](https://github.com/microservices-demo/orders)


## Build 

```
GROUP=weaveworksdemos COMMIT=MasterFachstudie ./scripts/build.sh
```


## Check

```
docker ps
```

Should show the newly created image `weaveworksdemos/orders:MasterFachstudie`


## Start Process
Change to demo application: 
[Link to Repo](hhttps://github.com/Kerberos3000/microservices-demo)

and start it.


## Remark

If you get the following error message:
```
cp: cannot create directory '…/orders/target/docker/': Permission denied
```
Try the following:

1. `sudo rm -r target`
2. `mkdir target`
3. build again 

It seems to be important that you manually create the target directory and not the script.
