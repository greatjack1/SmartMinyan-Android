package com.wyre.smartminyan.Models;

import java.util.Date;
import java.util.List;

/**
 * Created by yaakov on 4/2/18.
 */

public class Minyan {
    Date CreationTime;
    Date MinyanTime;
    int Id;
    double Latitude;
    double Longitude;
    String Address;
    String Description;
    User Creator;
    List<Comment> Comments;
    List<User> Commitments;

}
