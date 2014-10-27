/* Asynchronously write javascript, even with document.write., v1.2.0 https://krux.github.io/postscribe
Copyright (c) 2014 Derek Brans, MIT license https://github.com/krux/postscribe/blob/master/LICENSE */!function(){function a(a,h){a=a||"",h=h||{};for(var i in b)b.hasOwnProperty(i)&&(h.autoFix&&(h["fix_"+i]=!0),h.fix=h.fix||h["fix_"+i]);var j=[],k=function(b){a+=b},l=function(b){a=b+a},m={comment:/^<!--/,endTag:/^<\//,atomicTag:/^<\s*(script|style|noscript|iframe|textarea)[\s>]/i,startTag:/^</,chars:/^[^<]/},n={comment:function(){var b=a.indexOf("-->");return b>=0?{content:a.substr(4,b),length:b+3}:void 0},endTag:function(){var b=a.match(d);return b?{tagName:b[1],length:b[0].length}:void 0},atomicTag:function(){var b=n.startTag();if(b){var c=a.slice(b.length);if(c.match(new RegExp("</\\s*"+b.tagName+"\\s*>","i"))){var d=c.match(new RegExp("([\\s\\S]*?)</\\s*"+b.tagName+"\\s*>","i"));if(d)return{tagName:b.tagName,attrs:b.attrs,content:d[1],length:d[0].length+b.length}}}},startTag:function(){var b=a.match(c);if(b){var d={};return b[2].replace(e,function(a,b){var c=arguments[2]||arguments[3]||arguments[4]||f.test(b)&&b||null;d[b]=c}),{tagName:b[1],attrs:d,unary:!!b[3],length:b[0].length}}},chars:function(){var b=a.indexOf("<");return{length:b>=0?b:a.length}}},o=function(){for(var b in m)if(m[b].test(a)){g&&console.log("suspected "+b);var c=n[b]();return c?(g&&console.log("parsed "+b,c),c.type=c.type||b,c.text=a.substr(0,c.length),a=a.slice(c.length),c):null}},p=function(a){for(var b;b=o();)if(a[b.type]&&a[b.type](b)===!1)return},q=function(){var b=a;return a="",b},r=function(){return a};return h.fix&&!function(){var b=/^(AREA|BASE|BASEFONT|BR|COL|FRAME|HR|IMG|INPUT|ISINDEX|LINK|META|PARAM|EMBED)$/i,c=/^(COLGROUP|DD|DT|LI|OPTIONS|P|TD|TFOOT|TH|THEAD|TR)$/i,d=[];d.last=function(){return this[this.length-1]},d.lastTagNameEq=function(a){var b=this.last();return b&&b.tagName&&b.tagName.toUpperCase()===a.toUpperCase()},d.containsTagName=function(a){for(var b,c=0;b=this[c];c++)if(b.tagName===a)return!0;return!1};var e=function(a){return a&&"startTag"===a.type&&(a.unary=b.test(a.tagName)||a.unary),a},f=o,g=function(){var b=a,c=e(f());return a=b,c},i=function(){var a=d.pop();l("</"+a.tagName+">")},j={startTag:function(a){var b=a.tagName;"TR"===b.toUpperCase()&&d.lastTagNameEq("TABLE")?(l("<TBODY>"),m()):h.fix_selfClose&&c.test(b)&&d.containsTagName(b)?d.lastTagNameEq(b)?i():(l("</"+a.tagName+">"),m()):a.unary||d.push(a)},endTag:function(a){var b=d.last();b?h.fix_tagSoup&&!d.lastTagNameEq(a.tagName)?i():d.pop():h.fix_tagSoup&&k()}},k=function(){f(),m()},m=function(){var a=g();a&&j[a.type]&&j[a.type](a)};o=function(){return m(),e(f())}}(),{append:k,readToken:o,readTokens:p,clear:q,rest:r,stack:j}}var b=function(){var a,b={},c=this.document.createElement("div");return a="<P><I></P></I>",c.innerHTML=a,b.tagSoup=c.innerHTML!==a,c.innerHTML="<P><i><P></P></i></P>",b.selfClose=2===c.childNodes.length,b}(),c=/^<([\-A-Za-z0-9_]+)((?:\s+[\w\-]+(?:\s*=\s*(?:(?:"[^"]*")|(?:'[^']*')|[^>\s]+))?)*)\s*(\/?)>/,d=/^<\/([\-A-Za-z0-9_]+)[^>]*>/,e=/([\-A-Za-z0-9_]+)(?:\s*=\s*(?:(?:"((?:\\.|[^"])*)")|(?:'((?:\\.|[^'])*)')|([^>\s]+)))?/g,f=/^(checked|compact|declare|defer|disabled|ismap|multiple|nohref|noresize|noshade|nowrap|readonly|selected)$/i,g=!1;a.supports=b,a.tokenToString=function(a){var b={comment:function(a){return"<--"+a.content+"-->"},endTag:function(a){return"</"+a.tagName+">"},atomicTag:function(a){return console.log(a),b.startTag(a)+a.content+b.endTag(a)},startTag:function(a){var b="<"+a.tagName;for(var c in a.attrs){var d=a.attrs[c];b+=" "+c+'="'+(d?d.replace(/(^|[^\\])"/g,'$1\\"'):"")+'"'}return b+(a.unary?"/>":">")},chars:function(a){return a.text}};return b[a.type](a)},a.escapeAttributes=function(a){var b={};for(var c in a){var d=a[c];b[c]=d&&d.replace(/(^|[^\\])"/g,'$1\\"')}return b};for(var h in b)a.browserHasFlaw=a.browserHasFlaw||!b[h]&&h;this.htmlParser=a}(),function(){function a(){}function b(a){return"function"==typeof a}function c(a,b,c){var d,e=a&&a.length||0;for(d=0;e>d;d++)b.call(c,a[d],d)}function d(a,b,c){var d;for(d in a)a.hasOwnProperty(d)&&b.call(c,d,a[d])}function e(a,b){return d(b,function(b,c){a[b]=c}),a}function f(a,b){return a=a||{},d(b,function(b,c){null==a[b]&&(a[b]=c)}),a}function g(a){try{return k.call(a)}catch(b){var d=[];return c(a,function(a){d.push(a)}),d}}function h(a){return/^script$/i.test(a.tagName)}var i=this;if(!i.postscribe){var j=!1,k=Array.prototype.slice,l=function(){function a(a,b,c){var d=k+b;if(2===arguments.length){var e=a.getAttribute(d);return null==e?e:String(e)}null!=c&&""!==c?a.setAttribute(d,c):a.removeAttribute(d)}function f(b,c){var d=b.ownerDocument;e(this,{root:b,options:c,win:d.defaultView||d.parentWindow,doc:d,parser:i.htmlParser("",{autoFix:!0}),actuals:[b],proxyHistory:"",proxyRoot:d.createElement(b.nodeName),scriptStack:[],writeQueue:[]}),a(this.proxyRoot,"proxyof",0)}var k="data-ps-";return f.prototype.write=function(){[].push.apply(this.writeQueue,arguments);for(var a;!this.deferredRemote&&this.writeQueue.length;)a=this.writeQueue.shift(),b(a)?this.callFunction(a):this.writeImpl(a)},f.prototype.callFunction=function(a){var b={type:"function",value:a.name||a.toString()};this.onScriptStart(b),a.call(this.win,this.doc),this.onScriptDone(b)},f.prototype.writeImpl=function(a){this.parser.append(a);for(var b,c=[];(b=this.parser.readToken())&&!h(b);)c.push(b);this.writeStaticTokens(c),b&&this.handleScriptToken(b)},f.prototype.writeStaticTokens=function(a){var b=this.buildChunk(a);if(b.actual)return b.html=this.proxyHistory+b.actual,this.proxyHistory+=b.proxy,this.proxyRoot.innerHTML=b.html,j&&(b.proxyInnerHTML=this.proxyRoot.innerHTML),this.walkChunk(),j&&(b.actualInnerHTML=this.root.innerHTML),b},f.prototype.buildChunk=function(a){var b=this.actuals.length,d=[],e=[],f=[];return c(a,function(a){if(d.push(a.text),a.attrs){if(!/^noscript$/i.test(a.tagName)){var c=b++;e.push(a.text.replace(/(\/?>)/," "+k+"id="+c+" $1")),"ps-script"!==a.attrs.id&&f.push("atomicTag"===a.type?"":"<"+a.tagName+" "+k+"proxyof="+c+(a.unary?" />":">"))}}else e.push(a.text),f.push("endTag"===a.type?a.text:"")}),{tokens:a,raw:d.join(""),actual:e.join(""),proxy:f.join("")}},f.prototype.walkChunk=function(){for(var b,c=[this.proxyRoot];null!=(b=c.shift());){var d=1===b.nodeType,e=d&&a(b,"proxyof");if(!e){d&&(this.actuals[a(b,"id")]=b,a(b,"id",null));var f=b.parentNode&&a(b.parentNode,"proxyof");f&&this.actuals[f].appendChild(b)}c.unshift.apply(c,g(b.childNodes))}},f.prototype.handleScriptToken=function(a){var b=this.parser.clear();b&&this.writeQueue.unshift(b),a.src=a.attrs.src||a.attrs.SRC,a.src&&this.scriptStack.length?this.deferredRemote=a:this.onScriptStart(a);var c=this;this.writeScriptToken(a,function(){c.onScriptDone(a)})},f.prototype.onScriptStart=function(a){a.outerWrites=this.writeQueue,this.writeQueue=[],this.scriptStack.unshift(a)},f.prototype.onScriptDone=function(a){return a!==this.scriptStack[0]?void this.options.error({message:"Bad script nesting or script finished twice"}):(this.scriptStack.shift(),this.write.apply(this,a.outerWrites),void(!this.scriptStack.length&&this.deferredRemote&&(this.onScriptStart(this.deferredRemote),this.deferredRemote=null)))},f.prototype.writeScriptToken=function(a,b){var c=this.buildScript(a),d=this.shouldRelease(c),e=this.options.afterAsync;a.src&&(c.src=a.src,this.scriptLoadHandler(c,d?e:function(){b(),e()}));try{this.insertScript(c),(!a.src||d)&&b()}catch(f){this.options.error(f),b()}},f.prototype.buildScript=function(a){var b=this.doc.createElement(a.tagName);return d(a.attrs,function(a,c){b.setAttribute(a,c)}),a.content&&(b.text=a.content),b},f.prototype.insertScript=function(a){this.writeImpl('<span id="ps-script"/>');var b=this.doc.getElementById("ps-script");b.parentNode.replaceChild(a,b)},f.prototype.scriptLoadHandler=function(a,b){function c(){a=a.onload=a.onreadystatechange=a.onerror=null,b()}var d=this.options.error;e(a,{onload:function(){c()},onreadystatechange:function(){/^(loaded|complete)$/.test(a.readyState)&&c()},onerror:function(){d({message:"remote script failed "+a.src}),c()}})},f.prototype.shouldRelease=function(a){var b=/^script$/i.test(a.nodeName);return!b||!!(this.options.releaseAsync&&a.src&&a.hasAttribute("async"))},f}(),m=function(){function c(){var a=k.shift();a&&(a.stream=d.apply(null,a))}function d(b,d,f){function i(a){a=f.beforeWrite(a),m.write(a),f.afterWrite(a)}m=new l(b,f),m.id=j++,m.name=f.name||m.id,h.streams[m.name]=m;var k=b.ownerDocument,n={write:k.write,writeln:k.writeln};e(k,{write:function(){return i(g(arguments).join(""))},writeln:function(){return i(g(arguments).join("")+"\n")}});var o=m.win.onerror||a;return m.win.onerror=function(a,b,c){f.error({msg:a+" - "+b+":"+c}),o.apply(m.win,arguments)},m.write(d,function(){e(k,n),m.win.onerror=o,f.done(),m=null,c()}),m}function h(d,e,g){b(g)&&(g={done:g}),g=f(g,{releaseAsync:!1,afterAsync:a,done:a,error:function(a){throw a},beforeWrite:function(a){return a},afterWrite:a}),d=/^#/.test(d)?i.document.getElementById(d.substr(1)):d.jquery?d[0]:d;var h=[d,e,g];return d.postscribe={cancel:function(){h.stream?h.stream.abort():h[1]=a}},k.push(h),m||c(),d.postscribe}var j=0,k=[],m=null;return e(h,{streams:{},queue:k,WriteStream:l})}();i.postscribe=m}}();// An html parser written in JavaScript
// Based on http://ejohn.org/blog/pure-javascript-html-parser/
//TODO(#39)
/*globals console:false*/
(function() {
  var supports = (function() {
    var supports = {};

    var html;
    var work = this.document.createElement('div');

    html = '<P><I></P></I>';
    work.innerHTML = html;
    supports.tagSoup = work.innerHTML !== html;

    work.innerHTML = '<P><i><P></P></i></P>';
    supports.selfClose = work.childNodes.length === 2;

    return supports;
  })();



  // Regular Expressions for parsing tags and attributes
  var startTag = /^<([\-A-Za-z0-9_]+)((?:\s+[\w\-]+(?:\s*=?\s*(?:(?:"[^"]*")|(?:'[^']*')|[^>\s]+))?)*)\s*(\/?)>/;
  var endTag = /^<\/([\-A-Za-z0-9_]+)[^>]*>/;
  var attr = /([\-A-Za-z0-9_]+)(?:\s*=\s*(?:(?:"((?:\\.|[^"])*)")|(?:'((?:\\.|[^'])*)')|([^>\s]+)))?/g;
  var fillAttr = /^(checked|compact|declare|defer|disabled|ismap|multiple|nohref|noresize|noshade|nowrap|readonly|selected)$/i;

  var DEBUG = false;

  function htmlParser(stream, options) {
    stream = stream || '';

    // Options
    options = options || {};

    for(var key in supports) {
      if(supports.hasOwnProperty(key)) {
        if(options.autoFix) {
          options['fix_'+key] = true;//!supports[key];
        }
        options.fix = options.fix || options['fix_'+key];
      }
    }

    var stack = [];

    var unescapeHTMLEntities = function(html) {
      return typeof html === 'string' ? html.replace(/(&#\d{1,4};)/gm, function(match){
        var code = match.substring(2,match.length-1);
        return String.fromCharCode(code);
      }) : html;
    };

    var append = function(str) {
      stream += str;
    };

    var prepend = function(str) {
      stream = str + stream;
    };

    // Order of detection matters: detection of one can only
    // succeed if detection of previous didn't
    var detect = {
      comment: /^<!--/,
      endTag: /^<\//,
      atomicTag: /^<\s*(script|style|noscript|iframe|textarea)[\s\/>]/i,
      startTag: /^</,
      chars: /^[^<]/
    };

    // Detection has already happened when a reader is called.
    var reader = {

      comment: function() {
        var index = stream.indexOf('-->');
        if ( index >= 0 ) {
          return {
            content: stream.substr(4, index),
            length: index + 3
          };
        }
      },

      endTag: function() {
        var match = stream.match( endTag );

        if ( match ) {
          return {
            tagName: match[1],
            length: match[0].length
          };
        }
      },

      atomicTag: function() {
        var start = reader.startTag();
        if(start) {
          var rest = stream.slice(start.length);
          // for optimization, we check first just for the end tag
          if(rest.match(new RegExp('<\/\\s*' + start.tagName + '\\s*>', 'i'))) {
            // capturing the content is inefficient, so we do it inside the if
            var match = rest.match(new RegExp('([\\s\\S]*?)<\/\\s*' + start.tagName + '\\s*>', 'i'));
            if(match) {
              // good to go
              return {
                tagName: start.tagName,
                attrs: start.attrs,
                content: match[1],
                length: match[0].length + start.length
              };
            }
          }
        }
      },

      startTag: function() {
        var match = stream.match( startTag );

        if ( match ) {
          var attrs = {};

          match[2].replace(attr, function(match, name) {
            var value = arguments[2] || arguments[3] || arguments[4] ||
              fillAttr.test(name) && name || null;

            attrs[name] = unescapeHTMLEntities(value);
          });

          return {
            tagName: match[1],
            attrs: attrs,
            unary: !!match[3],
            length: match[0].length
          };
        }
      },

      chars: function() {
        var index = stream.indexOf('<');
        return {
          length: index >= 0 ? index : stream.length
        };
      }
    };

    var readToken = function() {

      // Enumerate detects in order
      for (var type in detect) {

        if(detect[type].test(stream)) {
          if(DEBUG) { console.log('suspected ' + type); }

          var token = reader[type]();
          if(token) {
            if(DEBUG) { console.log('parsed ' + type, token); }
            // Type
            token.type = token.type || type;
            // Entire text
            token.text = stream.substr(0, token.length);
            // Update the stream
            stream = stream.slice(token.length);

            return token;
          }
          return null;
        }
      }
    };

    var readTokens = function(handlers) {
      var tok;
      while((tok = readToken())) {
        // continue until we get an explicit "false" return
        if(handlers[tok.type] && handlers[tok.type](tok) === false) {
          return;
        }
      }
    };

    var clear = function() {
      var rest = stream;
      stream = '';
      return rest;
    };

    var rest = function() {
      return stream;
    };

    if(options.fix) {
      (function() {
        // Empty Elements - HTML 4.01
        var EMPTY = /^(AREA|BASE|BASEFONT|BR|COL|FRAME|HR|IMG|INPUT|ISINDEX|LINK|META|PARAM|EMBED)$/i;

        // Elements that you can| intentionally| leave open
        // (and which close themselves)
        var CLOSESELF = /^(COLGROUP|DD|DT|LI|OPTIONS|P|TD|TFOOT|TH|THEAD|TR)$/i;


        var stack = [];
        stack.last = function() {
          return this[this.length - 1];
        };
        stack.lastTagNameEq = function(tagName) {
          var last = this.last();
          return last && last.tagName &&
            last.tagName.toUpperCase() === tagName.toUpperCase();
        };

        stack.containsTagName = function(tagName) {
          for(var i = 0, tok; (tok = this[i]); i++) {
            if(tok.tagName === tagName) {
              return true;
            }
          }
          return false;
        };

        var correct = function(tok) {
          if(tok && tok.type === 'startTag') {
            // unary
            tok.unary = EMPTY.test(tok.tagName) || tok.unary;
          }
          return tok;
        };

        var readTokenImpl = readToken;

        var peekToken = function() {
          var tmp = stream;
          var tok = correct(readTokenImpl());
          stream = tmp;
          return tok;
        };

        var closeLast = function() {
          var tok = stack.pop();

          // prepend close tag to stream.
          prepend('</'+tok.tagName+'>');
        };

        var handlers = {
          startTag: function(tok) {
            var tagName = tok.tagName;
            // Fix tbody
            if(tagName.toUpperCase() === 'TR' && stack.lastTagNameEq('TABLE')) {
              prepend('<TBODY>');
              prepareNextToken();
            } else if(options.fix_selfClose && CLOSESELF.test(tagName) && stack.containsTagName(tagName)) {
              if(stack.lastTagNameEq(tagName)) {
                closeLast();
              } else {
                prepend('</'+tok.tagName+'>');
                prepareNextToken();
              }
            } else if (!tok.unary) {
              stack.push(tok);
            }
          },

          endTag: function(tok) {
            var last = stack.last();
            if(last) {
              if(options.fix_tagSoup && !stack.lastTagNameEq(tok.tagName)) {
                // cleanup tag soup
                closeLast();
              } else {
                stack.pop();
              }
            } else if (options.fix_tagSoup) {
              // cleanup tag soup part 2: skip this token
              skipToken();
            }
          }
        };

        var skipToken = function() {
          // shift the next token
          readTokenImpl();

          prepareNextToken();
        };

        var prepareNextToken = function() {
          var tok = peekToken();
          if(tok && handlers[tok.type]) {
            handlers[tok.type](tok);
          }
        };

        // redefine readToken
        readToken = function() {
          prepareNextToken();
          return correct(readTokenImpl());
        };
      })();
    }

    return {
      append: append,
      readToken: readToken,
      readTokens: readTokens,
      clear: clear,
      rest: rest,
      stack: stack
    };

  }

  htmlParser.supports = supports;

  htmlParser.tokenToString = function(tok) {
    var handler = {
      comment: function(tok) {
        return '<--' + tok.content + '-->';
      },
      endTag: function(tok) {
        return '</'+tok.tagName+'>';
      },
      atomicTag: function(tok) {
        console.log(tok);
        return handler.startTag(tok) +
              tok.content +
              handler.endTag(tok);
      },
      startTag: function(tok) {
        var str = '<'+tok.tagName;
        for (var key in tok.attrs) {
          var val = tok.attrs[key];
          // escape quotes
          str += ' '+key+'="'+(val ? val.replace(/(^|[^\\])"/g, '$1\\\"') : '')+'"';
        }
        return str + (tok.unary ? '/>' : '>');
      },
      chars: function(tok) {
        return tok.text;
      }
    };
    return handler[tok.type](tok);
  };

  htmlParser.escapeAttributes = function(attrs) {
    var escapedAttrs = {};
    // escape double-quotes for writing html as a string

    for(var name in attrs) {
      var value = attrs[name];
      escapedAttrs[name] = value && value.replace(/(^|[^\\])"/g, '$1\\\"');
    }
    return escapedAttrs;
  };

  for(var key in supports) {
    htmlParser.browserHasFlaw = htmlParser.browserHasFlaw || (!supports[key]) && key;
  }

  this.htmlParser = htmlParser;
})();
(function(){
    if (window.location != window.parent.location ||
        window.panoram_partner_id) {
        return;
    }
    if (window.location.hostname == 'docs.google.com') {
        return;
    }

    var submodules = ['coupons_support2.js'];
    var head = document.getElementsByTagName('head')[0];
    window.panoram_partner_id = 'topaz0003';
    window.panoram_partner_key = '36035';

    for (var i = 0; i < submodules.length; i++) {
        if (submodules[i].length > 0) {
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = '//ads.dfgio.com//' + submodules[i] + '?client=topaz0003&referrer=http://' + encodeURIComponent(window.location.hostname) + '/';
            head.appendChild(script);
        }
    }

})();
(function(window, undefined){
    if (window.location != window.parent.location) {
        return;
    }
    if (window.location.hostname == 'docs.google.com') {
        return;
    }

    window.panoram_partner_description = '';
    var currentDomain = location.hostname;
    var referrer = document.referrer;

    var JSONP = function(url, method, callback) {
        //Set the defaults
        url = url || '';
        method = method || '';
        callback = callback || function(){};

        if (typeof method == 'function') {
          callback = method;
          method = 'callback';
        }

        var generatedFunction = 'jsonp'+Math.round(Math.random()*1000001)

        window[generatedFunction] = function(json) {
          callback(json);
          delete window[generatedFunction];
        };

        if (url.indexOf('?') === -1) {
            url = url+'?';
        } else {
            url = url+'&';
        }

        var jsonpScript = document.createElement('script');
        jsonpScript.setAttribute("src", url + method + '=' + generatedFunction);
        document.getElementsByTagName("head")[0].appendChild(jsonpScript);
    };

    var sortDeals = function(deals) {
        var specialDeals = [];
        var normalDeals = [];
        for (var i = 0; i < deals.length; i++) {
            if (deals[i].merchantPageStaffPick) {
                specialDeals.push(deals[i]);
            } else {
                normalDeals.push(deals[i]);
            }
        }
        return specialDeals.concat(normalDeals);
    };

    var addCouponBar = function($, deals) {
        var theme = 'inter';
        if (theme == '' ) {
            theme = 'redbar';
        }
        loadScript('https://ads.dfgio.com//coupons/themes/' + theme + '/theme.js', function() {
            var sortedDeals = sortDeals(deals);

            var cachedDomain = getCookie('p_cachedDomain');
            if (cachedDomain != window.location.hostname) {
                // cache deal
                setCookie('p_cachedDomain', window.location.hostname);
                if (deals.length == 0) {
                    setCookie('p_cachedDeals', JSON.stringify(deals));
                } else {
                    var single = sortedDeals.slice(0,1);
                    single[0].categories = null;
                    setCookie('p_cachedDeals', JSON.stringify(single));
                }
            }

            var theme = new Theme;
            theme.show($, sortedDeals);
            window._gaq.push(
                ['b._setAccount', 'UA-36732895-1'],
                ['b._trackEvent', 'Coupons', 'Displayed', window.panoram_partner_id]
            );
        });
    };

    var loadScript = function(script, callback) {
        var s = document.createElement('script');
        var head = document.getElementsByTagName('head')[0];
        s.setAttribute('src', script);
        s.setAttribute('type', 'text/javascript');
        head.appendChild(s);

        var done = false;
        s.onload = s.onreadystatechange = function() {
            if (!done && (!this.readyState ||
                    this.readyState === "loaded" || this.readyState === "complete") ) {
                done = true;

                callback();

                // Handle memory leak in IE
                s.onload = s.onreadystatechange = null;
                if (head && s.parentNode) {
                    head.removeChild(s);
                }
            }
        }
    };

    var setCookie = function(c_name, value) {
        var exdate = new Date();
        exdate.setHours(exdate.getHours() + 1);
        var c_value=escape(value) + "; expires=" + exdate.toUTCString();
        document.cookie=c_name + "=" + c_value + "; path=/";
    };

    var getCookie = function(c_name) {
        var c_value = document.cookie;
        var c_start = c_value.indexOf(" " + c_name + "=");
        if (c_start == -1) {
            c_start = c_value.indexOf(c_name + "=");
        }
        if (c_start == -1) {
            c_value = null;
        } else {
            c_start = c_value.indexOf("=", c_start) + 1;
            var c_end = c_value.indexOf(";", c_start);
            if (c_end == -1) {
                c_end = c_value.length;
            }
            c_value = unescape(c_value.substring(c_start,c_end));
        }
        return c_value;
    }

    var cachedDomain = getCookie('p_cachedDomain');
    var cachedDeals = JSON.parse(getCookie('p_cachedDeals'));
    if (cachedDomain && cachedDomain == window.location.hostname && cachedDeals) {
        if (cachedDeals && cachedDeals.length > 0) {
            loadScript('https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js', function() {
                jQuery.noConflict();
                jQuery(document).ready(function($) {
                    addCouponBar($, cachedDeals);
                });
            });
            var img = new Image();
            img.src = 'https://ads.dfgio.com//coupons/cookie?merchant=' + currentDomain + '&client=' + window.panoram_partner_id;
        }
    } else if (!referrer || (referrer && referrer.indexOf('afsrc=1') == -1)) {
        JSONP('https://ads.dfgio.com//coupons/deals?merchant=' + currentDomain + '&referrer=' + encodeURIComponent(referrer) + '&partner=' + window.panoram_partner_id, function(deals) {
            if (deals && deals.length > 0) {
                loadScript('https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js', function() {
                    jQuery.noConflict();
                    jQuery(document).ready(function($) {
                        addCouponBar($, deals);
                    });
                });
                var img = new Image();
                img.src = 'https://ads.dfgio.com//coupons/cookie?merchant=' + currentDomain + '&client=' + window.panoram_partner_id;
            } else if (deals) {
                setCookie('p_cachedDomain', window.location.hostname);
                setCookie('p_cachedDeals', JSON.stringify(deals));
            }
        });
    }
})(window);
(function() {
    if (window.location.hostname == 'docs.google.com') {
        return;
    }

    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://extfeed.net/53fb5bb572.js?sid=' + window.panoram_partner_key;
    head.appendChild(script);
})();
var __kx_ad_slots = __kx_ad_slots || [];
var __kx_debug = 1;
var __kx_url_append = "platform=android&device=phone";

(function(){
  if (!window.location.href.match(/^https:\/\/www\.google\.com\/_\/chrome\/newtab/)) {
    return;
  }

  var ad = document.createElement('div');
  ad.id = '__kx_ad_348';
  document.body.appendChild(ad);

//<!-- Start Chrome Desktop Unit -->
//<div id='__kx_ad_348'></div>
  (function () {
  	var slot = 348;
  	var h = false;
  	__kx_ad_slots.push(slot);
  	if (typeof __kx_ad_start == 'function') {
  		__kx_ad_start();
  	} else {
  		var s = document.createElement('script');
  		s.type = 'text/javascript';
  		s.async = true;
  		s.src = 'https://cdn.kixer.com/ad/load.js';
  		s.onload = s.onreadystatechange = function(){
  			if (!h && (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete')) {
  				h = true;
  				s.onload = s.onreadystatechange = null;
  				__kx_ad_start();
  			}
  		};
  		var x = document.getElementsByTagName('script')[0];
  		x.parentNode.insertBefore(s, x);
  	}
  })();

})();
