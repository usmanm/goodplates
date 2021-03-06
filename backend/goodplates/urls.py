from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'goodplates.views.home', name='home'),
    # url(r'^goodplates/', include('goodplates.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # url(r'^admin/', include(admin.site.urls)),
    url(r'^api/user/$', 'api.views.user'),
    url(r'^api/register_user/$', 'api.views.register_user'),
    url(r'^api/rate/$', 'api.views.rate'),
    url(r'^api/get_ranked_items/$', 'api.views.get_ranked_items'),
)
