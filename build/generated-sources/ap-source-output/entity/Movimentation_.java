package entity;

import entity.Book;
import entity.Customer;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-25T22:50:48")
@StaticMetamodel(Movimentation.class)
public class Movimentation_ { 

    public static volatile SingularAttribute<Movimentation, Date> date;
    public static volatile SingularAttribute<Movimentation, Customer> customerId;
    public static volatile SingularAttribute<Movimentation, Integer> id;
    public static volatile ListAttribute<Movimentation, Book> bookList;

}