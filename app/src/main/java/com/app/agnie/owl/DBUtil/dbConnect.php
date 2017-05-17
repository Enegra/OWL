<?php
 //Defining Constants
 define('HOST','stasis.eu');
 define('USER','srv18048_user');
 define('PASS','7ZfSHin8T3kA');
 define('DB','srv18048_owl');

 //Connecting to Database
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');