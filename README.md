# Network control

Main goal of this Java API is provide full network control from scratch i. e create docker
networks, containers, connect them together as they are in topology,
calculate shortest paths, set routes to routers (container) routing table, simulate 
crash on link.

Currently supports:

* Physical network creating including routers
* Calculation shortest paths
* Update routers routing tables
* Set default gateways on routers
* Deploy Java application into docker container

## Getting Started

These instructions will get you a copy of the project up
and running on your local machine for development and testing purposes.

### Prerequisites
Before installing you need to download, compile and install network simulator:
```
git clone https://github.com/PatrIoT-Framework/network-simulator-example.git
cd network-simulator-example
mvn assembly:assembly
mvn install:install-file -Dfile=target/network-sample-2.0-SNAPSHOT-jar-with-dependencies.jar -DgroupId=com.redhat.patriot -DartifactId=network-sample -Dversion=2.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true

```

### Installing

All necessary libraries can be installed by:
```
git clone https://github.com/obabec/network-control.git
mvn install
```

### Running tests

```
mvn test
```

### Build With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Network simulator](https://github.com/PatrIoT-Framework/network-simulator-example) - Java network simulator
* [network-control-api](https://github.com/obabec/network-control-api) - Iproute2, iptables api servers

