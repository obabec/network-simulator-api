# Network control

Main goal of this Java API is provide full topologyNetwork control from scratch i. e create docker
networks, containers, connect them together as they are in networkTop,
calculate shortest paths, set routes to routers (container) routing table, simulate 
crash on link.

Currently supports:

* Physical topologyNetwork creating including routers
* Calculation shortest paths
* Update routers routing tables
* Set default gateways on routers
* Deploy Java application into docker container

## Getting Started

These instructions will get you a copy of the project up
and running on your local machine for development and testing purposes.

### Prerequisites
Before installing you need to download, compile and install topologyNetwork simulator:
```
git clone https://github.com/PatrIoT-Framework/topologyNetwork-simulator-example.git
cd topologyNetwork-simulator-example
mvn assembly:assembly
mvn install:install-file -Dfile=target/topologyNetwork-sample-2.0-SNAPSHOT-jar-with-dependencies.jar -DgroupId=com.redhat.patriot -DartifactId=topologyNetwork-sample -Dversion=2.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true

```

### Router
Before creating topologyNetwork topology, router image has to be build.
```
git clone https://github.com/obabec/PatrIoT_router
docker build -t YOURTAG PatrIoT_router
```
You will use tag of your image when you will create topology and
especially routers. 


### Running tests

```
mvn test
```

### Build With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Network simulator](https://github.com/PatrIoT-Framework/topologyNetwork-simulator-example) - Java topologyNetwork simulator
* [topologyNetwork-control-api](https://github.com/obabec/topologyNetwork-control-api) - Iproute2, iptables api servers

