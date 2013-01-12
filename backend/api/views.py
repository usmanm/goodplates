# Create your views here.
from django.http import HttpResponse
from api.models import User

import json

def user_json(registered):
	return json.dumps({"registered": registered}, indent=4)
def user(request, username):
	try:
		user = User.objects.get(username=username)
		return HttpResponse(user_json(registered=True))
	except User.DoesNotExist:
		return HttpResponse(user_json(registered=False))

def register_user(request, username):
	u = User(username=username)
	u.save()
	return HttpResponse(user_json(registered=True))
