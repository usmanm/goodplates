# Create your views here.
from django.http import HttpResponse, HttpResponseBadRequest
from api.models import User

import json

def user_json(registered):
	return json.dumps({"registered": registered}, indent=4)
def user(request):
	try:
		username = request.GET["username"]
		user = User.objects.get(username=username)
		return HttpResponse(user_json(registered=True))
	except User.DoesNotExist:
		return HttpResponse(user_json(registered=False))
	except KeyError:
		return HttpResponseBadRequest("username required but not provided")

def register_user(request):
	try:
		username = request.GET["username"]
	except KeyError:
		return HttpResponseBadRequest("username required but not provided")
	u = User(username=username)
	u.save()
	return HttpResponse(user_json(registered=True))
