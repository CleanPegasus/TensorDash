from setuptools import setup

def readme():
    with open('README.md') as f:
        README = f.read()
    return README


setup(

    name = "tensor-dash",
    version = "1.5",
    description = "A python package that lets you remotely monitor your deep learning training metrics through it's companion app.",
    long_description = readme(),
    long_description_content_type = "text/markdown",
    url = "https://github.com/CleanPegasus/TensorDash",
    authors = "Harshit Maheshwari, Arunkumar L",
    author_email = "hmhmrock@gmail.com, arunk609@gmail.com",
    license = "MIT",
    classifiers = [
        "Programming Language :: Python :: 3",
        "Programming Language :: Python :: 3.7"
    ],
    packages = ["tensordash"],
    include_package_data = True,
    install_requires = ["requests"]
    )