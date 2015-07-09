
var App = React.createClass({
  getInitialState: function() {
    return {key: 1};
  },
  handleSelect: function(key) {
	  alert(key);
    this.setState({key});
    this.handleSelect;
  },
// componentDidMount: function() {
// this.state.key = 1;
// },
  render: function() {
    return (
    	<div className="container">
    		<div className="header clearfix">
    			<h3 className="text-muted">React with Scala.js</h3>
				<Nav bsStyle="pills" ref="nav" >
					<NavItemLink to="home"> Home </NavItemLink>
					<NavItemLink to="demo"> Demo </NavItemLink>					
					<NavItemLink to="about"> About </NavItemLink>
				</Nav>
			</div>
			<div id="content"><RouteHandler /></div>			
			<footer className="footer"><p>&copy; Tileline.inc 2015</p></footer>
		</div>
    );
  }
});

var HomePage = React.createClass({
//  componentDidMount: function() {
//	  React.findDOMNode(App).setState({key:1});
//  },
  render: function() {
    return (
      <Jumbotron>
	    <h1>Welcome</h1>
	    <p>This is a simple Project for React.js with Scala.js </p>
	    <p>
		   <ButtonLink to="demo" bsStyle='primary' params={{ someparam: '' }}>Try Demo</ButtonLink>
	    </p>
	   </Jumbotron>
    );
  }
});
var DemoPage = React.createClass({
  render: function() {
    return (
       <CommentBox url="url" pollInterval={10000} />
    );
  }
});

var AboutPage = React.createClass({
  render: function() {
    return (
      <Alert bsStyle='warning'>
	    <strong>React.js Sample Project!</strong> Cominucation with Server by Ajax API implemented Scala.js .
	  </Alert>
    );
  }
});

var routes = (
  <Route handler={App} path="/">
  	<Route name="home" path="/" handler={HomePage} />
    <Route name="demo" path="demo" handler={DemoPage} />
  	<Route name="about" path="about" handler={AboutPage} />
  </Route>
);

Router.run(routes, function (Handler) {
  React.render(<Handler/>,  document.body);
});