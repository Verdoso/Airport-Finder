# Airport-Finder
A simple project to use as a tool to practice the lessons learned at the SUSE Cloud Native Foundations Scholarship.

If you want to test it, you'll need to have an account for the MaxMind GeoIP2 service (free account) see https://www.maxmind.com/en/geoip2-services-and-databases.

##Testing in Kubernetes
Once you have vagrant up & running using the Vagrantfile in the home directory and you have k3s installed ([installation instructions here](https://rancher.com/docs/k3s/latest/en/installation/) ):

**Note:** There is a bug in Windows with Hypervisor & K3s so until is is solved, you should install version v1.20.7+k3s1 with `curl -sfL https://get.k3s.io | INSTALL_K3S_VERSION=v1.20.7+k3s1 sh`

Move to the `/vagrant/deployment` directory

### Create the namespace
`kubectl apply -f namespace.yaml`

### Create the empty secrets holder
`kubectl apply -f airport-finder-secrets.yaml`

### Add the real secrets
Create a file named geoip2.account-id with the id, and another one named geoip2.license-key with the license key, of your MaxMind GeoIP2 service account.

No worries, git is configured to ignore those files so they won't be added to the source repository.

### Add the id and license to the secrets
Run the following command to patch the secrets with the data:
 `kubectl create secret generic airport-finder-secrets --save-config --dry-run=client --from-file=./geoip2.account-id --from-file=./geoip2.license-key -o yaml | kubectl apply -f -`

 ### Deploy the application
`kubectl apply -f airport-finder-deployment.yaml`

 ### Create the service to support the application
`kubectl apply -f airport-finder-service.yaml`

### Set up your default context to be 'scnfs-demo'
`kubectl config set-context --current --namespace=scnfs-demo`

### Prepare the script to find the pod name to use in commands'
`alias findpod='POD=$(kubectl get pod -l app=airport-finder -o jsonpath="{.items[0].metadata.name}")'`

### Check that the pod and the service are up & running
`kubectl get all`

### Check that the pod details
`findpod;kubectl describe po $POD`

### Check pod logs
`findpod;kubectl logs --follow $POD`

### Open a terminal in the log, in case you need to check things
`findpod;kubectl exec --stdin --tty $POD -- /bin/bash`

### To remove the service and deployment
`kubectl delete -f airport-finder-deployment.yaml`

`kubectl delete -f airport-finder-service.yaml`

## Creating the docker image locally
`mvn package jib:dockerBuild`
