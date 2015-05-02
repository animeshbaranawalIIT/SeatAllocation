from django.conf.urls import patterns, include, url
from django.contrib import admin
from lab.views import display,formfill,signup,change_pwd,real_change_pwd,signin,opener,enter_rank,update,logout,baser,showprog,homeop,addpref,submit_pref,final_pref,rmv_pref,my_pref
urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'lab.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
    url(r'^dis/$',display),
    url(r'^formfill/$',formfill),
    url(r'^signup/$',signup),
    url(r'^signin/$',signin),
    url(r'^change_pwd/$',change_pwd),
    url(r'^real_change_pwd/$',real_change_pwd),
    url(r'^opener/$',opener),
    url(r'^base/$',baser),
    url(r'^enter_rank/$',enter_rank),
    url(r'^showprog/$',showprog),
    url(r'^update/$',update),
    url(r'^homeop/$',homeop),
	url(r'^logout/$',logout),
    url(r'^addpref/$',addpref),
    url(r'^submit_pref/$',submit_pref),
    url(r'^final_pref/$',final_pref),
    url(r'^rmv_pref/$',rmv_pref),
    url(r'^my_pref/$',my_pref),


)
