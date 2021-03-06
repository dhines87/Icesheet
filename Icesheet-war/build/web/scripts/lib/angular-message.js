
/*
 AngularJS v1.4.4
 (c) 2010-2015 Google, Inc. http://angularjs.org
 License: MIT
*/
(function (A, h, B) {
    'use strict'; function v(h) {
        return ["$animate", function (q) {
            return {
                restrict: "AE", transclude: "element", terminal: !0, require: "^^ngMessages", link: function (m, e, a, g, k) {
                    var c = e[0], n, l = a.ngMessage || a.when; a = a.ngMessageExp || a.whenExp; var p = function (d) { n = d ? w(d) ? d : d.split(/[\s,]+/) : null; g.reRender() }; a ? (p(m.$eval(a)), m.$watchCollection(a, p)) : p(l); var f, r; g.register(c, r = {
                        test: function (d) { var b = n; d = b ? w(b) ? 0 <= b.indexOf(d) : b.hasOwnProperty(d) : void 0; return d }, attach: function () {
                            f || k(m, function (d) {
                                q.enter(d,
								null, e); f = d; f.on("$destroy", function () { f && (g.deregister(c), r.detach()) })
                            })
                        }, detach: function () { if (f) { var d = f; f = null; q.leave(d) } }
                    })
                }
            }
        }]
    } var w = h.isArray, x = h.forEach, y = h.isString, z = h.element; h.module("ngMessages", []).directive("ngMessages", ["$animate", function (h) {
        function q(e, a) { return y(a) && 0 === a.length || m(e.$eval(a)) } function m(e) { return y(e) ? e.length : !!e } return {
            require: "ngMessages", restrict: "AE", controller: ["$element", "$scope", "$attrs", function (e, a, g) {
                function k(a, d) {
                    for (var b = d, e = []; b && b !== a;) {
                        var c =
						b.$$ngMessageNode; if (c && c.length) return l[c]; b.childNodes.length && -1 == e.indexOf(b) ? (e.push(b), b = b.childNodes[b.childNodes.length - 1]) : b = b.previousSibling || b.parentNode
                    }
                } var c = this, n = 0, l = this.messages = {}, p, f; this.render = function (r) {
                    r = r || {}; p = !1; f = r; for (var d = q(a, g.ngMessagesMultiple) || q(a, g.multiple), b = [], n = {}, s = c.head, k = !1, l = 0; null != s;) { l++; var t = s.message, u = !1; k || x(r, function (b, a) { !u && m(b) && t.test(a) && !n[a] && (u = n[a] = !0, t.attach()) }); u ? k = !d : b.push(t); s = s.next } x(b, function (b) { b.detach() }); b.length !==
					l ? h.setClass(e, "ng-active", "ng-inactive") : h.setClass(e, "ng-inactive", "ng-active")
                }; a.$watchCollection(g.ngMessages || g["for"], c.render); this.reRender = function () { p || (p = !0, a.$evalAsync(function () { p && f && c.render(f) })) }; this.register = function (a, d) { var b = n.toString(); l[b] = { message: d }; var g = e[0], f = l[b]; c.head ? (g = k(g, a)) ? (f.next = g.next, g.next = f) : (f.next = c.head, c.head = f) : c.head = f; a.$$ngMessageNode = b; n++; c.reRender() }; this.deregister = function (a) {
                    var d = a.$$ngMessageNode; delete a.$$ngMessageNode; var b = l[d]; (a =
					k(e[0], a)) ? a.next = b.next : c.head = b.next; delete l[d]; c.reRender()
                }
            }]
        }
    }]).directive("ngMessagesInclude", ["$templateRequest", "$document", "$compile", function (h, q, m) { return { restrict: "AE", require: "^^ngMessages", link: function (e, a, g) { var k = g.ngMessagesInclude || g.src; h(k).then(function (c) { m(c)(e, function (c) { a.after(c); c = z(q[0].createComment(" ngMessagesInclude: " + k + " ")); a.after(c); a.remove() }) }) } } }]).directive("ngMessage", v("AE")).directive("ngMessageExp", v("A"))
})(window, window.angular);
//# sourceMappingURL=angular-messages.min.js.map
