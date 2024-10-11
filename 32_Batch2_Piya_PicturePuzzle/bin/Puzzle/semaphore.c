#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

// Semaphore to control printer access
sem_t printer;

// Function for printing task
void* print_job(void* id) {
    int user = *(int*)id;
    
    printf("User %d is waiting...\n", user);

    // Wait for the printer to be free
    sem_wait(&printer);

    // Critical section: only one user prints at a time
    printf("User %d is printing...\n", user);
    sleep(2);  // Simulate printing time

    printf("User %d is done printing.\n", user);

    // Release the printer
    sem_post(&printer);

    return NULL;
}

int main() {
    pthread_t threads[3];
    int ids[3] = {1, 2, 3};

    // Initialize the semaphore (1 printer available)
    sem_init(&printer, 0, 1);

    // Create threads for each user
    for (int i = 0; i < 3; i++) {
        pthread_create(&threads[i], NULL, print_job, &ids[i]);
    }

    // Wait for all users to finish printing
    for (int i = 0; i < 3; i++) {
        pthread_join(threads[i], NULL);
    }

    // Clean up the semaphore
    sem_destroy(&printer);

    return 0;
}
