using System.ServiceModel;
using System.ServiceModel.Web;
using PineapplePiranha.Model;

namespace PineapplePiranha.Service
{    
    [ServiceContract(Namespace="http://pineapplepiranha.com/services/location")]    
    public interface IUserLocationService
    {        
        [OperationContract]
        [WebGet(UriTemplate="LastKnownLocation/{userName}", ResponseFormat=WebMessageFormat.Json, BodyStyle=WebMessageBodyStyle.WrappedRequest)]
        UserLocation GetLastKnownUserLocation(string userName);

        [OperationContract]
        [WebInvoke(Method="POST", UriTemplate="LastKnownLocation/add/{userName}", RequestFormat=WebMessageFormat.Json, ResponseFormat=WebMessageFormat.Json, BodyStyle=WebMessageBodyStyle.Wrapped)]
        bool SetLastKnownUserLocationDetails(string longitude, string latitude, string altitude, string confidence, string userName);

        /*[OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "LocationService/SetLastKnownLocationFull/{userName}", RequestFormat = WebMessageFormat.Json, ResponseFormat = WebMessageFormat.Json)]
        bool SetLastKnownUserLocationDetailsFull(string userName, string longitude, string latitude, string altitude, string confidence, string locationId);
        */
    }    
}
