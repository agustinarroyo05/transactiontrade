class TransactionList extends React.Component {
	
	constructor(props) {
	    super(props);
	    this.state = {'txList' : []};
	  }

	async componentDidMount(){
		try {
		      const response = await fetch('transactiontrade/transactions');
		      if (!response.ok) {
		        throw Error(response.statusText);
		      }
		      const data = await response.json();
			  this.setState({'txList' : data});   
				
		    } catch (error) {
		      console.log(error);
		 }
	}
	
	render() {
		const listItems = this.state.txList.map((tx) =>
			<Transaction amount={tx.amount} type={tx.type} id={tx.id} efectiveDate={tx.efectiveDate}/>
		);
		return <ul className='list-group,table, ulwrap'>{listItems}</ul>;
	}
}

class Transaction extends React.Component{

  constructor(props) {
    super(props);
    this.state = {isToggleOn: false, amount : props.amount, type : props.type, id : props.id, efectiveDate : props.efectiveDate};

    this.showDetailsBtn = this.showDetailsBtn.bind(this);
  }

  showDetailsBtn(){
   this.setState(state => ({
      isToggleOn: !state.isToggleOn
    }));
  }
  render(){
	  if (this.state.isToggleOn){
		  return(<li className='list-group-item'> 	
		  	<button onClick={this.showDetailsBtn}>{this.state.isToggleOn ? '-' : '+'}</button>
		  	<span className='ppal'>Type: {this.state.type}</span> 
		  	<span className={this.state.type === 'DEBIT' ? 'negcred' : ''} > Amount: {this.state.amount}</span>
		  	<div className='details'>
				<span> Id: {this.state.id} </span>
				<span> Time : {this.state.efectiveDate} </span>
			</div>
		 </li>);
		}
		else
			return(<li className='list-group-item'> 	
				<button onClick={this.showDetailsBtn}>{this.state.isToggleOn ? '-' : '+'}</button>
			  	<span className='ppal'>Id: {this.state.id}</span> 
			  	<span className={this.state.type === 'DEBIT' ? 'negcred' : ''} > Amount: {this.state.amount}</span>
		 </li>);

	}
}

ReactDOM.render(
  <TransactionList />,
  document.getElementById('root')
);