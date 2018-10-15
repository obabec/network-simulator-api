package com.redhat.patriot.network_controll.gateway;

import com.redhat.patriot.network_simulator.example.container.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * The type Gw controller.
 */
public class GwDemo {

        /**
         * Start gateway. Sort of GW demo.
         *
         * @throws InterruptedException the interrupted exception
         */
        public void startGateway() throws InterruptedException {
            final Logger logger = LoggerFactory.getLogger(GwDemo.class);
            AppManager appManager = new AppManager();
            String name = "gatewayIP";
            appManager.newApp(name)
                    .from("ubuntu:16.04")
                    .run(Arrays.asList(
                            "apt-get update",
                            "apt-get install -y --no-install-recommends locales",
                            "locale-gen en_US.UTF-8",
                            "apt-get dist-upgrade -y",
                            "apt-get --purge remove openjdk*",
                            " echo \"oracle-java8-installer shared/accepted-oracle-license-v1-1 select true\"" +
                                    " | debconf-set-selections",
                            "echo \"deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main\" " +
                                    "> /etc/apt/sources.list.d/webupd8team-java-trusty.list",
                            "apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886",
                            "apt-get update",
                            "apt-get install -y --no-install-recommends " +
                                    "oracle-java8-installer oracle-java8-set-default",
                            "apt-get clean all"
                    ))
                    .run(Arrays.asList("apt-get -y update", "apt-get -y install git maven " +
                            "iptables vim curl python iproute2 python-pip"))
                    .workdir("/")
                    .run(Arrays.asList(
                            "curl -O https://storage.googleapis.com/golang/go1.9.1.linux-amd64.tar.gz",
                            "tar -xvf go1.9.1.linux-amd64.tar.gz",
                            "mv go /usr/local"
                    ))
                    .env("PATH", "$PATH:/usr/local/go/bin ")
                    .run(Arrays.asList("pip install pyroute2", "pip install flask"))
                    .run("git clone https://github.com/obabec/iproute-rest.git")
                    .env("FLASK_APP", "/iproute-rest/app.py")
                    .run("git clone https://github.com/obabec/iptables-api.git")
                    .run(Arrays.asList(
                            "go get -u github.com/oxalide/go-iptables/iptables",
                            "go get -u github.com/abbot/go-http-auth",
                            "go get -u github.com/gorilla/handlers",
                            "go get -u github.com/gorilla/mux"
                    ))
                    .workdir("/iptables-api/")
                    .run("go build -o iptables-api")
                    .workdir("/")
                    .run("git clone " +
                            "https://github.com/ficap/smart-home-com.redhat.patriot.network_controll.gateway.git")
                    .run(Arrays.asList("cd smart-home-com.redhat.patriot.network_controll.gateway",
                            "git checkout dummy-test", "mvn package"))
                    .entrypoint("tail -f /dev/null")
                    .workdir("/iptables-api");
            appManager.setTag("app_gateway");
            appManager.setStartCommand(name, "bash");
            AppConfig appConfig = appManager.deploy(name);
            String iptablesCommand = "./iptables-api -ip " + appConfig.getIPAdd();
            appManager.executeCommand(appConfig, iptablesCommand);
            String iprouteCommand = "flask run --host=0.0.0.0";
            appManager.executeCommand(appConfig, iprouteCommand);
            logger.info("IP is: " + appConfig.getIPAdd());

    }
}
