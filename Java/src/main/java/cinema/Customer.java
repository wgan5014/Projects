package cinema;

public class Customer {
	private String username;
	private String pwd;
	private CreditCard cards;

	public Customer(String user, String pwd, CreditCard creditCard) {
		this.username = user;
		this.pwd = pwd;
		this.cards = creditCard;
	}

	public Customer(String user, String pwd) {
		this.username = user;
		this.pwd = pwd;
		this.cards = null;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.pwd;
	}

	public CreditCard getCard() { return cards;	}

	public void setCard(String name, int text) {
		if(this.cards == null) {
			this.cards = new CreditCard(name, text);
		}
		else {
			cards.setCardHolder(name);
			cards.setCardNumber(text);
		}
	}
}
