**1.** Run HttpListener with Tomcat;

**2.** Install ActiveMQ:
wget -c "http://www.apache.org/dyn/closer.cgi?filename=/activemq/5.15.2/apache-activemq-5.15.2-bin.tar.gz&action=download" -O apache-activemq-5.15.2-bin.tar.gz
tar xzf apache-activemq-5.15.2-bin.tar.gz
mv apache-activemq-5.15.2 activemq
cd activemq/bin/linux-x86-64
./activemq start