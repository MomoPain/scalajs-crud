var Validator = {
	hasError() {
		return this.state.errors.length > 0;
	},
	setErrors(errors) {
		this.setState({
			errors: errors
		});			
	},
	validationState() {
		return this.getBsStyle(this.state.errors);
	},
	_validate(value) {
		var errors = new Array();
		if(this.props.required == true) {
			this.required(value, errors);
		}
		this.setState({valid:true})
		this.setErrors(errors);
		return errors.length > 0;
	},
	validate() {
		return this._validate(this.state.value);
	},
	required(value, errors) {
		if(value == '') {
			errors[errors.length] = {required:'必須'};
		}
	}
};

var TextInput = React.createClass({
	mixins:[Validator],
	getInitialState() {
		return {
			value: this.props.value,
			valid: false,
			errors: new Array()
		}
	},
	clear() {
		this.setState({
			value : '',
			valid : false,
			errors: new Array()
		});		
	},	
	getBsStyle(errors) {
		if(this.state.valid == false) {
			return '';
		}
		if(errors.length > 0) {			
			return 'error';
		} else {			
			return 'success';
		}
	},
	handleChange(event) {
		var v =  event.target.value
		this.setState({value : v});
		this._validate(v);
	},
	render() {
	    return (
	      <Input 
	        type='text' 
	        value={this.state.value}
	      	placeholder={this.props.label}
	        label={this.props.label}
	        help={this.props.help}
	        bsStyle={this.validationState()}
	        hasFeedback
	        ref='input'
	        groupClassName='group-class'
	        labelClassName='label-class'
	        onChange={this.handleChange} 
	      />
	    );
	  }
});
