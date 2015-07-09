
 
var CommentList = React.createClass({
  render: function() {
    var commentNodes = this.props.data.map(function (comment) {
      return (
        <Comment key={comment.key} index={comment.key}author={comment.author} comment={comment.text}>
          
        </Comment>
      );
    });
    return (
    <table className="table">
    	<thead>
    	<th>#</th>
    	<th>Name</th>
    	<th>Comment</th>
    	</thead>
    	<tbody>
    	{commentNodes}
    	</tbody>
    </table>
    );
  }
});

var CommentForm = React.createClass({
	handleSubmit: function(e) {
	    e.preventDefault();
	    var author = React.findDOMNode(this.refs.author).value.trim();
	    var text = React.findDOMNode(this.refs.text).value.trim();
	    if (!text || !author) {
	      return;
	    }
	    this.props.onCommentSubmit({author: author, text: text});
	    React.findDOMNode(this.refs.author).value = '';
	    React.findDOMNode(this.refs.text).value = '';
	    return;
	  },
  render: function() {
    return (
	<form className="form-inline" onSubmit={this.handleSubmit}>
	  <div className="form-group">
	    <input type="text" className="form-control" ref="author" placeholder="Your Name"/>
	  </div>
	  <div className="form-group">
	    <input type="text" className="form-control" ref="text" placeholder="Say someting..."/>
	  </div>
	  <Button type="submit" bsStyle="success">Post</Button>
	</form>

    );
  }
});


var CommentBox = React.createClass({
	  loadCommentsFromServer: function() {
		  //call ajax api that is generated from scala.js
		  client.CommentAction().list(this);
	  },
	  handleCommentSubmit: function(comment) {
		  // call ajax api that is generated from scala.js
		  client.CommentAction().update(comment, this);
		  var comments = this.state.data;
		  var newComments = comments.concat([comment]);
		  this.setState({data: newComments});
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
		       <CommentList data={this.state.data} />	        
			</div>
	        
	      </div>
	    );
	  }
	});

var Comment = React.createClass({
	  render: function() {
		// var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
	    return (
	      <tr>
	      <td>{this.props.index}</td>
	      <td>{this.props.author}</td>
	      <td>{this.props.comment}</td>
	      </tr>	      
	    );
	  }
	});
//
//React.render(
//  <CommentBox url="url" pollInterval={10000} />,
//  document.getElementById('content')
//);
