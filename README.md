# library_case_study
This is a library management case study created for microservice

Sample Subscription request to subscribe is:

{
"subscriberName": "Dilip",
"dateSubscribed": "30-Aug-2020",
"dateReturned": null,
"bookId": "B4232"
}

Known issue: Since using in memory DB, if 2 book-service is up then book store may have inconsistent value.

