package hello;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @CrossOrigin
  @RequestMapping("/greeting")
  public Greeting[] greeting(@RequestParam(value="name", defaultValue="World") String name) {
    Greeting[] greetingar = new Greeting[2];
    greetingar[0] = new Greeting(counter.incrementAndGet(),String.format(template, name));
    greetingar[1] = new Greeting(counter.incrementAndGet(),String.format(template, name));
    return greetingar;
  }
  /*
  @CrossOrigin
  @RequestMapping("/productCatalog")
  public List<ProductCatalog> getProduct(@RequestParam(value="commodity", defaultValue="53101501") String commodity) {
    List<ProductCatalog> productCatalog;
    productCatalog = getProductCatalog(commodity);
    return productCatalog;
  }
  public List<ProductCatalog> getProductCatalog(String commodity){
    List<ProductCatalog> productCatalog = new ArrayList<>();

    String url = "jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50000/BLUDB";
        Connection conn = null;
        try {
            conn =  DriverManager.getConnection(url, "xnj68236", "x+wz8d02rf6wktbl");
            System.out.println("successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        String selectSQL = "SELECT * FROM XNJ68236.XXIBM_PRODUCT_CATALOG";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
          pstmt = conn.prepareStatement(selectSQL);
          rs = pstmt.executeQuery();

          while (rs.next()) {
              System.out.println(rs.getString("SEGMENT_NAME"));
              ProductCatalog pc = new ProductCatalog(rs.getInt("SEGMENT_NAME"),rs.getString("SEGMENT_NAME"),rs.getInt("SEGMENT_NAME"),rs.getString("SEGMENT_NAME"),rs.getInt("SEGMENT_NAME"),rs.getString("SEGMENT_NAME"),rs.getInt("SEGMENT_NAME"),rs.getString("SEGMENT_NAME"));
              
          }
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      } 
    return productCatalog;
  }*/
}