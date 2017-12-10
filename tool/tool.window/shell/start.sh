pidlist=`ps -ef |grep network_window  |grep -v "grep"|awk '{print $2}'`   
kill $pidlist
java -jar /home/sherlock/software/network_window/network_window.jar & 