# KafkaLearnings

Before running these examples, make sure you install the kafka in your system.</br>
Here, the kafka is running in 9093 port. You can change the port of running in your application. default broker port is 9092</br>
To install the kafka you can go through [Apache Kafka](https://kafka.apache.org/quickstart) or if you are interested in the [confluent open source edition](https://docs.confluent.io/3.3.0/installation/installing_cp.html) </br>
To change the port, specify in the server.properties of the kafka "port=<Portnumber>". </br>



## 1. Logging
maven project example [kafka log4j appender](https://github.com/sanit4u/KafkaLearnings/tree/master/test) </br>
Spring boot example [kafka log4j appender](https://github.com/sanit4u/KafkaLearnings/tree/master/com.demo.test.elk) </br>


## 2. producer & Consumer
[simple project producer & Consumer](https://github.com/sanit4u/KafkaLearnings/tree/master/com.test.java.kafka)</br>
spring boot [Consumer](https://github.com/sanit4u/KafkaLearnings/blob/master/KafkaConsumer.java) & its [Configuration](https://github.com/sanit4u/KafkaLearnings/blob/master/KafkaConsumerConfiguration.java). [Producer](https://github.com/sanit4u/KafkaLearnings/blob/master/KafkaSender2.java) & it's [configuration](https://github.com/sanit4u/KafkaLearnings/blob/master/KafkaConfiguration.java)
