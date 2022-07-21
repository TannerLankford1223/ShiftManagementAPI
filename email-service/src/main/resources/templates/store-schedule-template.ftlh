<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Weekly Schedule Email</title>
</head>
<body>
<p> ${employeeName},</p>

<p>Below are your shifts for the week of <a> ${(startOfWeek).format('MMM d, yyyy')}</a>: </p>

<#list shifts as shift>
    <h3 style="color: green">shift ID#${shift.shiftId}: </h3>
    <p> &ensp; <b>Day:</b> ${(shift.shiftDate).format('EEEE, MMM d, yyyy')} </p>
    <p> &ensp; <b>Shift start time:</b> ${(shift.startTime).format('h:mm a')}</p>
    <p> &ensp; <b>Shift end time:</b> ${(shift.endTime).format('h:mm a')}</p>
    <p> &ensp; <b>Store ID number:</b> ${shift.storeId} </p>
    <br/>
</#list>
<br/>

<p> If you have any questions or concerns please contact us at <a> ${contactInformation}</a></p>

<p>Regards,</p>
<p>
    <em> ${companyName}</em>

    <br/>
</p>
</body>
</html>