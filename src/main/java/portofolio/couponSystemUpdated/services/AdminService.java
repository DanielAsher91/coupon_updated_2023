package portofolio.couponSystemUpdated.services;

import portofolio.couponSystemUpdated.entities.Company;
import portofolio.couponSystemUpdated.entities.Coupon;
import portofolio.couponSystemUpdated.entities.Customer;
import portofolio.couponSystemUpdated.repositories.CompanyRepository;
import portofolio.couponSystemUpdated.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService extends ClientService {
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CompanyRepository companyRepository;

    private final String email = "admin@admin.com";
    private final String password = "admin";

    public Company addCompany(String name, String email, String password) {
        List<Company> allCompanies = companyRepository.findAll();
        for (Company company : allCompanies) {
            if (company.getName().equals(name) || company.getEmail().equals(email)) {
                System.out.println("Either Name or Email is all ready in use!");
                return null;
            }
        }Company company = Company.builder().name(name).email(email).password(password).build();
        addCompany(company);
        System.out.println("Company " + company.getName() + "has been added successfully!");
        return company;
    }

    public Company addCompany1(Company addedCompany) {
        List<Company> allCompanies = companyRepository.findAll();
        for (Company company : allCompanies) {
            if (company.getName().equals(addedCompany.getName()) || company.getEmail().equals(addedCompany.getEmail())) {
                System.out.println("Either Name or Email is all ready in use!");

                return null;
            }
        }Company newCompany = Company.builder().name(addedCompany.getName()).email(addedCompany.getEmail()).password(addedCompany.getPassword()).build();
        addCompany(newCompany);
        System.out.println("Company " + newCompany.getName() + "has been added successfully!");
        return newCompany;
    }







    public void addNewCompany(String name, String email, String password) {
        List<Company> allCompanies = companyRepository.findAll();
        for (Company company : allCompanies) {
            if (company.getName().equals(name) || company.getEmail().equals(email)) {
                System.out.println("Either Name or Email is all ready in use!");
                return;
            }
        }Company company = Company.builder().name(name).email(email).password(password).build();
        addCompany(company);
        System.out.println("Company " + company.getName() + "has been added successfully!");
    }
    public Company updateCompany(int companyId, String email) {
        if (companyRepository.findById(companyId).isEmpty()) {
            System.out.println("Wrong ID entered!");
            return null;
        }Company company = companyRepository.findById(companyId).get();
        company.setEmail(email);
        addCompany(company);
        System.out.println("Company with ID : " + companyId + " has been updated!");
        return company;
    }



    public int deleteExistsCompany(int companyId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            System.out.println("Wrong ID Entered!");
            return 0;
        }Company company = companyRepository.findById(companyId).get();
        List<Coupon> companyCoupons = company.getCompanyCoupons();
        for (Coupon coupon : companyCoupons) {
            couponRepository.deleteById(coupon.getId());
        }companyRepository.deleteById(companyId);
        System.out.println("Compnay with id " + companyId + " has been deleted!");
        return 1;
    }


     public List<Company> getAllCompanies() {
        return companyRepository.findAll();
     }

//     public Company getOneCompany(int companyId) {
//        return companyRepository.findById(companyId).orElse(Company.builder().name("N/A").email("N/A").password("N/A").build());
//     }

    public Optional<Company> getOneCompany(int companyId) {
        return companyRepository.findById(companyId);
    }

     public Customer addNewCustomer(String firstName, String lastName, String email, String password) {
        List<Customer> allCustomers = getAllCustomers();
         for (Customer customer : allCustomers) {
             if (customer.getEmail().equals(email)) {
                 System.out.println("Email : " + email + " is all ready in use!");
                 return null;
             }
         }Customer newCustomer = Customer.builder().firstName(firstName).lastName(lastName).email(email).password(password).build();
         addCustomer(newCustomer);
         System.out.println("The Customer has been added!");
         return  newCustomer;
     }
    public Customer addCustomerController(Customer customer) {

        List<Customer> allCustomers = customerRepository.findAll();
        System.out.println(allCustomers);
        for (Customer existCustomer : allCustomers) {
            System.out.println(existCustomer+ "From here");
            if (existCustomer.getEmail().equals(customer.getEmail())) {
                System.out.println("Email : " + email + " is all ready in use!");

                return null;
            }
        }Customer newCustomer = Customer.builder().firstName(customer.getFirstName()).lastName(customer.getLastName()).email(customer.getEmail()).password(customer.getPassword()).build();
        System.out.println("IM herre 3");
        addCustomer(newCustomer);
        System.out.println("The Customer has been added!");
        return newCustomer;
    }


     public void updateCustomer(int customerId,String firstName, String lastName, String email) {
         if (getOneCustomer(customerId).isPresent()) {
             Customer updatedCustomer = getOneCustomer(customerId).get();
             List<Customer> allCustomers = getAllCustomers();
             for (Customer customer : allCustomers) {
                 if (customer.getEmail().equals(email)) {
                     System.out.println("Email : " + email + " is all ready in use!");
                     return;
                 }
             }
             updatedCustomer.setFirstName(firstName);
             updatedCustomer.setLastName(lastName);
             updatedCustomer.setEmail(email);
             addCustomer(updatedCustomer);
             System.out.println("The Customer has been updated!");
             return;
         }
         System.out.println("No Customer with this ID : " + customerId);
     }



    public Customer updateCustomer1(int customerId, String firstName, String lastName, String email) {
        if (customerRepository.findById(customerId).isEmpty()) {
            System.out.println("Wrong ID entered");
            return null;
        }Customer customer = customerRepository.findById(customerId).get();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        addCustomer(customer);
        System.out.println("Customer with ID : " + customerId + " has been updated!");
        return customer;
    }

    public int updateCustomerController(int customerId,String firstName, String lastName, String email) {
        System.out.println("From Service: " + customerId);
        if (getOneCustomer(customerId).isPresent()) {
            Customer updatedCustomer = getOneCustomer(customerId).get();
            List<Customer> allCustomers = getAllCustomers();
            for (Customer customer : allCustomers) {
                if (customer.getEmail().equals(email)) {
                    System.out.println("Email : " + email + " is all ready in use!");
                    return 0;
                }
            }
            updatedCustomer.setFirstName(firstName);
            updatedCustomer.setLastName(lastName);
            updatedCustomer.setEmail(email);
            addCustomer(updatedCustomer);
            System.out.println("The Customer has been updated!");
            return 1 ;
        }
        System.out.println("No Customer with this ID : " + customerId);
        return 2;
    }

     public int deleteExistingCustomer(int customerId) {
         if (getOneCustomer(customerId).isPresent()) {
             Customer deletedCustomer = getOneCustomer(customerId).get();

             addCustomer(deletedCustomer);
             deleteCustomer(customerId);
             System.out.println("The customer with ID : " + customerId + " has been deleted!");
             return 0;
         }
         System.out.println("No Customer with this ID : " + customerId);
         return 1;
     }

     public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
     }

    public Company getCompanyByID(int companyId) {
        if (companyRepository.findById(companyId).isEmpty()) {
            System.out.println("Wrong ID entered!");
            return null;
        }return companyRepository.findById(companyId).get();
    }

    public Customer getCustomerByID(int customerId) {
        if (customerRepository.findById(customerId).isEmpty()) {
            System.out.println("Wrong ID entered!");
            return null;
        }return customerRepository.findById(customerId).get();
    }





}
