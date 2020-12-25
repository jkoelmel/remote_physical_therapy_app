##Backend
Run Server to get started. In a browser, go to http://localhost:8080/api/database/version to test the connection. If you see "MySQL Version 8.0.20" then success!

To see an example user payload, query http://localhost:8080/api/example/user

---

####PT
To add a PT to the database, the api endpoint to use is `/api/pt/register` with the query parameters `email, password, f_name, l_name, company`. This should be a POST request.

To retrieve all users who are PT's, use the endpoint `/api/pt/all`. This should be a GET request.

To retrieve a specific PT, use the endpoint `/api/pt/email` with the query parameter `email`. This should be a GET request.

To retrieve the patients belonging to a specific PT, use the endpoint `api/pt/patients` with the query parameter `pt_id`. This should be a GET request.

#####Sample JSON data

```
[
    {
        "pt_id": 1,
        "user": 251,
        "user_id": 251,
        "email": "jsmith@gmail.com",
        "f_name": "John",
        "l_name": "Smith",
        "company": "HealQuik",
        "secret": "passwordEncryption"
    }
]
```

---

####Patient
To add a Patient to the database, the api endpoint to use is `/api/patient/register` with the query parameters `email, password, f_name, l_name, company`. This should be a POST request.

Patients can be updated to add a PT link by using `api/patient/update-pt` with query parameters `patient_id, pt, prospective_pt`. This should be a PUT request.

Patients can be retrieved by calling a GET request on `api/patient/all` for all patients, or `api/patient/id` with the query parameter `patient_id` for a specific one.

#####Sample JSON data

```
[
    {
        "patient_id": 1,
        "user": 497,
        "pt": 1,
        "prospective_pt": 0,
        "user_id": 497,
        "email": "jsmith@hotmail.com",
        "f_name": "John",
        "l_name": "Smith",
        "company": "Lazzy",
        "secret": "passwordEncryption"
    }
]
```

---

####Entries
To add a patient entry log, send a POST request to the endpoint `api/patient/entry/register` with the query parameters `entry, patient_id`.

To retrieve a specific entry, use a GET request at `api/patient/entry/id` with the query parameter `entry_id`. To retrieve all entries by a specific patient, use a GET request at `api/patient/entry/all` with the query parameter `patient_id`.

#####Sample JSON data

```
[
    {
        "entry_id": 17,
        "entry": "imperdiet et commodo vulputate justo in blandit ultrices enim lorem ipsum dolor sit amet consectetuer adipiscing elit proin interdum",
        "created_on": "May 5, 2020, 4:50:52 AM",
        "patient": 100
    }
]
```
