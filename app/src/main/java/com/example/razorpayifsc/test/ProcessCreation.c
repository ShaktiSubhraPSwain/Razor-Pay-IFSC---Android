//
// Created by S.Shubhra Swain on 11/07/22.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

static int staticData = 111;
int main() {
    int stackData = 222;
    char processName[10] = "";
    printf("before forking...\n");
    int childPID = fork();
    printf("after forking return value: %d\n...", childPID);
    switch(childPID) {
        case -1:
            printf("Error happening after forking");
            exit(EXIT_FAILURE);
        case 0:
            stackData = staticData * 3;
            staticData = stackData * 3;
        default:
            sleep(3);
            break;
    }

    if (childPID == 0) strcpy( processName, "Child : ");
    else               strcpy( processName, "Parent : ");

    printf("Process : %s", processName );
	printf("\t PID = %u, PPID = %u, stackData = %d, staticData = %d\n",
		getpid(), getppid(), stackData, staticData);

    getchar();
    return 0;
}