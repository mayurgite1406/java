<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Employee Details</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            text-align: center;
        }

        .container {
            background-color: white;
            width: 400px;
            margin: 50px auto;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0 10px gray;
        }

        p {
            font-size: 17px;
            text-align: left;
            margin: 15px;
        }

        a {
            display: inline-block;
            margin-top: 15px;
            color: blue;
        }
    </style>
</head>

<body>

    <div class="container">

        <h2>Employee Details</h2>

        <p>
            <b>Employee ID:</b>
            ${param.employeeId}
        </p>

        <p>
            <b>Employee Name:</b>
            ${param.employeeName}
        </p>

        <p>
            <b>Department:</b>
            ${param.department}
        </p>

        <p>
            <b>Designation:</b>
            ${param.designation}
        </p>

        <p>
            <b>Email:</b>
            ${param.email}
        </p>

        <p>
            <b>Request Method:</b>
            ${pageContext.request.method}
        </p>

        <a href="index.html">Back to Form</a>

    </div>

</body>
</html>