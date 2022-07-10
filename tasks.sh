#!/bin/bash

BASE_URL="http://localhost:8080/tasks"

if [ $# -gt 0 ]; then
    if [ $1 = '-h' -o $1 = '--h' -o $1 = '-help'  -o $1 = '--help' -o $1 = 'help' -o $1 = '-H' -o $1 = '--H' -o $1 = '-HELP'  -o $1 = '--HELP' -o $1 = 'HELP' ]; then
        echo -e "For help: $0 -help\n"
        echo -e "Notes:\n\t1. This script rely on CURL, please make sure to install CURL first, also make sure firewall allowing the port!\n\t     Debian/Ubuntu:      sudo apt install curl\n\t     RHEL/CentOS/Fedora: sudo yum install curl\n\t2. Please start the rest server first!\n\t3. The default URL is $BASE_URL, if you want to run this script from a remote server, please manual modify the value of the URL variable: BASE_URL\n"
        echo -e "Usage:\n\t$0 add \"task title\" 21/08/2019\n\t$0 list\n\t$0 list --expiring-today\n\t$0 done 3\n\t$0 delete 3\n"
    elif [ $1 = "add" ]; then
        if [ $# -gt 2 ]; then
            curl -X POST -H "Content-type:application/x-www-form-urlencoded" $BASE_URL -d "title=$2" -d "date=$3"
        else
            echo -e "Command not support: [$0 $@]  Please check help by input: $0 -help\n"
        fi
    elif [ $1 = "list" ]; then
        if [ $# -gt 1 ]; then
            curl -G $BASE_URL -d "filter=$2"
        else
            curl -G $BASE_URL
        fi
    elif [ $1 = "done" ]; then
        if [ $# -gt 1 ]; then
            curl -X PUT -H "Content-type:application/x-www-form-urlencoded" $BASE_URL -d 'status=done' -d "id=$2"
        else
            echo -e "Command error! Please check help by input: $0 -help\n"
        fi
    elif [ $1 = "delete" ]; then
        if [ $# -gt 1 ]; then
            curl -X DELETE "${BASE_URL}/id/$2"
        else
            echo -e "Command error! Please check help by input: $0 -help\n"
        fi
    else
        echo -e "Command not support: [$0 $@] Please check help by input: $0 -help\n"
    fi
else
    echo -e "For help: $0 -help\n"
    echo -e "Notes:\n\t1. This script rely on CURL, please make sure to install CURL first!\n\t     Debian/Ubuntu:      sudo apt install curl\n\t     RHEL/CentOS/Fedora: sudo yum install curl\n\t2. Please start the rest server first!\n\t3. The default URL is $BASE_URL, if you want to run this script from a remote server, please manual modify the value of the URL variable: BASE_URL\n"
    echo -e "Usage:\n\t$0 add \"task title\" 21/08/2019\n\t$0 list\n\t$0 list --expiring-today\n\t$0 done 3\n\t$0 delete 3\n"
fi
