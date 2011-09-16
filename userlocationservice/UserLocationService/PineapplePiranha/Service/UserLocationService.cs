using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Data.SqlClient;
using System.Configuration;
using MySql.Data.MySqlClient;
using PineapplePiranha.Model;
using System.ServiceModel.Activation;


namespace PineapplePiranha.Service
{
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.PerCall)]
    public class UserLocationService : IUserLocationService
    {
        public UserLocation GetLastKnownUserLocation(string userName)
        {
            //THIS IS AWFUL!!!
            UserLocation location = new UserLocation();
            String sql = "SELECT Longitude, Latitude, Altitude, Confidence, UserName FROM Locations WHERE LocationId IN (SELECT LastKnownLocation FROM Users WHERE UserName = @userName)";

            MySqlConnection conn = new MySqlConnection(ConfigurationManager.ConnectionStrings["pineapplePiranha"].ConnectionString);
            using (conn)
            {
                conn.Open();
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.Add(new MySqlParameter("userName", userName));
                MySqlDataReader reader = cmd.ExecuteReader();

                if (reader.Read())
                {

                    location.Longitude = reader.GetDecimal(0);
                    location.Latitude = reader.GetDecimal(1);
                    location.Altidude = reader.GetDecimal(2);
                    location.Confidence = reader.GetDecimal(3);
                    location.UserName = reader.GetString(4);

                    Console.Out.WriteLine(location.Longitude);
                    Console.Out.WriteLine(location.Latitude);
                    Console.Out.WriteLine(location.Altidude);
                    Console.Out.WriteLine(location.Confidence);
                    Console.Out.WriteLine(location.UserName);
                }
            }

            return location;
        }



        public bool SetLastKnownUserLocationDetails(string longitude, string latitude, string altitude, string confidence,string userName)
        {
            UserLocation location = GetUserLocationFromStrings(latitude, longitude, altitude, confidence, userName);
            return SetLasKnownUserLocation(location);
        }

        /*public bool SetLastKnownUserLocationDetailsFull(String userName, String longitude, String latitude, String altitude, String confidence, String locationId)
        {
            UserLocation location = GetUserLocationFromStrings(latitude, longitude, altitude, confidence, userName);
            int locId = int.Parse(locationId);
            return SetLastKnownUserLocation(location, locId);            
        }
        */
        private UserLocation GetUserLocationFromStrings(string longitude, string latitude, string altitude, string confidence, string userName)
        {
            return new UserLocation(decimal.Parse(latitude), decimal.Parse(longitude), decimal.Parse(altitude), decimal.Parse(confidence), userName);
        }

        private bool SetLasKnownUserLocation(Model.UserLocation location)
        {
            int locationId = GetLocationId();
            return SetLastKnownUserLocation(location, locationId);
        }

        private int GetLocationId()
        {
            int result = 0;
            //THIS IS AWFUL!!!
            UserLocation location = new UserLocation();
            String sql = "SELECT MAX(LocationId) as ID FROM Locations";

            MySqlConnection conn = new MySqlConnection(ConfigurationManager.ConnectionStrings["pineapplePiranha"].ConnectionString);
            using (conn)
            {
                conn.Open();
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                result = (int)cmd.ExecuteScalar();
            }

            return ++result;
        }

        private bool SetLastKnownUserLocation(UserLocation location, int locationId)
        {
            bool completed = false;
            //THIS IS AWFUL!!!            
            String sql = @"INSERT INTO Locations (LocationId, Longitude, Latitude, Altitude, Confidence, UserName) 
                           VALUES(@id, @longitude, @latitude, @altitude, @confidence, @userName); 
                           UPDATE Users 
                           SET LastKnownLocation = @id WHERE UserName = @userName;";


            MySqlConnection conn = new MySqlConnection(ConfigurationManager.ConnectionStrings["pineapplePiranha"].ConnectionString);
            using (conn)
            {
                conn.Open();
                MySqlCommand cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.Add(new MySqlParameter("id", locationId));
                cmd.Parameters.Add(new MySqlParameter("longitude", location.Longitude));
                cmd.Parameters.Add(new MySqlParameter("latitude", location.Latitude));
                cmd.Parameters.Add(new MySqlParameter("altitude", location.Altidude));
                cmd.Parameters.Add(new MySqlParameter("confidence", location.Confidence));
                cmd.Parameters.Add(new MySqlParameter("userName", location.UserName));

                int result = cmd.ExecuteNonQuery();
                if (result > 0)
                {
                    completed = true;
                }
            }

            return completed;
        }
    }
}
