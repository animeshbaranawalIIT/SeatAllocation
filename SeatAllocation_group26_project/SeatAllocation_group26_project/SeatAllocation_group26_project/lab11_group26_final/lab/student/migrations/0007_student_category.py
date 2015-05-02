# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('student', '0006_auto_20141031_1728'),
    ]

    operations = [
        migrations.AddField(
            model_name='student',
            name='category',
            field=models.CharField(max_length=6, default=''),
            preserve_default=True,
        ),
    ]
