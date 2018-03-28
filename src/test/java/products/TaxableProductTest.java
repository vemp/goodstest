package products;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import testData.TestData;

public class TaxableProductTest {

	public TaxableProduct[] prod = new TaxableProduct[6];
	public BigDecimal exp;
	public BigDecimal expZero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

	@Before
	public void prepareTestObjects() {
		prod = new TestData().getProd();
	}
	
	@Test
	public void testCalcShelfPrice() {
		assertEquals("An exempt, non-imported product should return 0 for total tax", expZero, prod[0].calcShelfPrice());
		
		// create a new product each time with different combinations
		exp = new BigDecimal(16.49).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non exempt, non-imported product with net price 14,99 should return 16,49 for shelf price", exp, prod[1].calcShelfPrice());
		
		exp = new BigDecimal(10.50).setScale(2, RoundingMode.HALF_UP);
		assertEquals("An exempt, imported product with net price 10,00 should return 10,50 for shelf price", exp, prod[2].calcShelfPrice());

		exp = new BigDecimal(54.65).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non-exempt, imported product with net price 47,50 should return 54,65 for shelf price", exp, prod[3].calcShelfPrice());
		
		exp = new BigDecimal(32.19).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non-exempt, imported product with net price 27,99 should return 32,19 for shelf price", exp, prod[4].calcShelfPrice());
		
		// let's try changing the rates and see if everything still works as expected
		exp = new BigDecimal(151).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non-exempt, imported product with net price 100,00 should return 151,00 for shelf price with rates set to 22 and 29 percent", exp, prod[5].calcShelfPrice());
	}

	@Test
	public void testCalcTax() {
		assertEquals("An exempt product should return 0 for basic sales tax", expZero, prod[0].calcTax(TaxableProduct.BASIC_SALES_TAX_KEY));
		assertEquals("A non-imported product should return 0 for import duties", expZero, prod[0].calcTax(TaxableProduct.IMPORT_DUTIES_TAX_KEY));
		
		// create a new product each time with different combinations
		exp = new BigDecimal(1.50).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A product with net price 14,99 should return 1,50 for basic sales tax", exp, prod[1].calcTax(TaxableProduct.BASIC_SALES_TAX_KEY));
		
		exp = new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP);
		assertEquals("An exempt product with net price 10,00 should return 0 for basic sales tax", expZero, prod[2].calcTax(TaxableProduct.BASIC_SALES_TAX_KEY));
		assertEquals("An imported product with net price 10,00 should return 0,50 for import duties", exp, prod[2].calcTax(TaxableProduct.IMPORT_DUTIES_TAX_KEY));

		exp = new BigDecimal(4.75).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A product with net price 47,50 should return 4,75 for basic sales tax", exp, prod[3].calcTax(TaxableProduct.BASIC_SALES_TAX_KEY));
		exp = new BigDecimal(2.40).setScale(2, RoundingMode.HALF_UP);
		assertEquals("An imported product with net price 47,50 should return 2,40 for import duties", exp, prod[3].calcTax(TaxableProduct.IMPORT_DUTIES_TAX_KEY));
		
		exp = new BigDecimal(2.80).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A product with net price 27,99 should return 2,80 for basic sales tax", exp, prod[4].calcTax(TaxableProduct.BASIC_SALES_TAX_KEY));
		exp = new BigDecimal(1.40).setScale(2, RoundingMode.HALF_UP);
		assertEquals("An imported product with net price 27,99 should return 1,40 for import duties", exp, prod[4].calcTax(TaxableProduct.IMPORT_DUTIES_TAX_KEY));
		
		// let's try changing the rates and see if everything still works as expected
		exp = new BigDecimal(22).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A product with net price 100,00 should return 22,00 for basic sales tax with the rate set to 22 percent", exp, prod[5].calcTax(TaxableProduct.BASIC_SALES_TAX_KEY));
		exp = new BigDecimal(29).setScale(2, RoundingMode.HALF_UP);
		assertEquals("An imported product with net price 100,00 should return 29,00 for import duties with the rate set to 29 percent", exp, prod[5].calcTax(TaxableProduct.IMPORT_DUTIES_TAX_KEY));
	}

	@Test
	public void testCalcTotalTaxes() {
		assertEquals("An exempt, non-imported product should return 0 for total tax", expZero, prod[0].calcTotalTaxes());
		
		// create a new product each time with different combinations
		exp = new BigDecimal(1.50).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non exempt, non-imported product with net price 14,99 should return 1,50 for total tax", exp, prod[1].calcTotalTaxes());
		
		exp = new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP);
		assertEquals("An exempt, imported product with net price 10,00 should return 0,50 for total tax", exp, prod[2].calcTotalTaxes());

		exp = new BigDecimal(7.15).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non-exempt, imported product with net price 47,50 should return 7,15 for total tax", exp, prod[3].calcTotalTaxes());
		
		exp = new BigDecimal(4.20).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non-exempt, imported product with net price 27,99 should return 4,20 for total tax", exp, prod[4].calcTotalTaxes());
		
		// let's try changing the rates and see if everything still works as expected
		exp = new BigDecimal(51).setScale(2, RoundingMode.HALF_UP);
		assertEquals("A non-exempt, imported product with net price 100,00 should return 51,00 for total tax with rates set to 22 and 29 percent", exp, prod[5].calcTotalTaxes());
	}

}
