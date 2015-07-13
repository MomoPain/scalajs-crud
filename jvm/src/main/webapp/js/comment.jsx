
 
var CommentList = React.createClass({
  render: function() {
	var fanc = this.props.onDeleteSubmit; 
    var commentNodes = this.props.data.map(function (comment) {
      return (
        <Comment 
        	key={comment.key}
        	index={comment.key}
        	author={comment.author}
    		comment={comment.text}
        	onDeleteSubmit={fanc}
        >          
        </Comment>
      );
    });
    return (
    <Table striped bordered condensed hover>
    	<thead>
    	<th>#</th>
    	<th>Name</th>
    	<th>Comment</th>
    	<th>Action</th>
    	</thead>
    	<tbody>
    	{commentNodes}
    	</tbody>
    </Table>
    );
  }
});

var CommentForm = React.createClass({
	getInitialState() {		
		return {
			errors: [],
			bsStyle: 'success'
		}
	},
//	handleValidate() {
//		console.log('handle button');
//		var author = this.refs.author;
//		var text = this.refs.text;				
//		if (author.hasError() || text.hasError()) {
//			this.setState({bsStyle:'error'});
//		} else {
//			this.setState({bsStyle:'success'});
//		}
//		console.log(this.state.bsStyle);
//	},
	handleSubmit(e) {
		e.preventDefault();
		var author = this.refs.author;
		var text = this.refs.text;				
		if (author.validate() || text.validate()) {
			console.log('error');
			return;
		}    
		this.props.onCommentSubmit({author: author.state.value, text: text.state.value});
		author.clear();
		text.clear();
		return;
	},
  render() {
    return (
	<form className="form-inline" onSubmit={this.handleSubmit}>
	  <div className="form-group">
	  	<TextInput 
	  		value=''
	  		placeholder='Enter Author'
	  		label='Name'	  			
	  		ref='author'
	  		required={true}	
	  	/>
	  </div>
	  <div className="form-group">
		<TextInput 
			value=''
			placeholder='Say somting'
			label=''	  			
			ref='text'
			required={false}	
		/>
	  </div>
	  <ButtonInput type="submit" bsStyle={this.state.bsStyle} value="Post"/>
	</form>

    );
  }
});


var CommentBox = React.createClass({
	  loadCommentsFromServer: function() {
		  // call ajax api that is generated from scala.js
		  client.CommentAction().list(this);
	  },
	  handleCommentSubmit: function(comment) {
		  // call ajax api that is generated from scala.js
		  client.CommentAction().update(comment, this);
		  var comments = this.state.data;
		  var newComments = comments.concat([comment]);
		  this.setState({data: newComments});
	  },
	  handleDeleteSubmit : function(index) {
		  // call ajax api that is generated from scala.js
		  client.CommentAction().delete(index, this);
	  },
	  getInitialState: function() {
	    return {data: []};
	  },
	  componentDidMount: function() {
	    this.loadCommentsFromServer();
	    setInterval(this.loadCommentsFromServer, this.props.pollInterval);
	  },
	  render: function() {
	    return (
	      <div>
			<div className="panel panel-info">
			  <div className="panel-heading">
			  	<h3 className="panel-title">Put comments.</h3>
			  </div>
			  <div className="panel-body">
		        <CommentForm onCommentSubmit={this.handleCommentSubmit} />		        
		       </div>
		       <CommentList data={this.state.data} onDeleteSubmit={this.handleDeleteSubmit} />	        
			</div>
	        
	      </div>
	    );
	  }
	});

var CommentDeleteButton = React.createClass({	
	handleSubmit(e) {
		this.props.onDeleteSubmit(this.props.index);				
	},
	render() {
		return (
			<Button 
				bsSize="xsmall" 
				bsStyle="warning"
				onClick={this.handleSubmit}>Delete</Button>
		);
	}
});

var Comment = React.createClass({
  render: function() {
	// var rawMarkup = marked(this.props.children.toString(), {sanitize:
// true});
    return (
      <tr>
      <td>{this.props.index}</td>
      <td>{this.props.author}</td>
      <td>{this.props.comment}</td>
      <td><CommentDeleteButton index={this.props.index} onDeleteSubmit={this.props.onDeleteSubmit} /></td>
      </tr>	      
    );
  }
})

