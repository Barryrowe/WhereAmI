using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Runtime.Serialization;
using System.Text;

namespace PineapplePiranha.Model
{
    [DataContract]
    public class UserLocation
    {
        [DataMember]
        public decimal Latitude { get; set; }
        [DataMember]
        public decimal Longitude { get; set; }
        [DataMember]
        public decimal Altidude { get; set; }
        [DataMember]
        public decimal Confidence { get; set; }
        [DataMember]
        public String UserName { get; set; }

        public UserLocation(decimal lat, decimal longi, decimal alt, decimal confidence, String userName)
        {
            Latitude = lat;
            Longitude = longi;
            Altidude = alt;
            Confidence = confidence;
            UserName = userName;
        }
        
        public UserLocation()
        {
            Latitude = 0;
            Longitude = 0;
            Altidude = 0;
            Confidence = 0;
        }

        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Latitude:");
            sb.Append(Latitude);
            sb.Append(",Longitude:");
            sb.Append(Longitude);
            sb.Append(",Altitude:");
            sb.Append(Altidude);
            sb.Append(",Confidence:");
            sb.Append(Confidence * 100);
            sb.Append("%,UserName:");
            sb.Append(UserName);

            return sb.ToString();
        }
    }
}